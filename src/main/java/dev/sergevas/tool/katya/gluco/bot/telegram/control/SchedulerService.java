package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.TriggerEvent;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.XDripReadingContext;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.ReadingService;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.Optional;

public class SchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

    private final KatyaGlucoBot katyaGlucoBot;
    private final ReadingService readingService;
    private final SchedulerControls schedulerControls;
    private final Long periodAccelerated;
    private final Long periodDefault;
    private final Long periodAlert;

    private Instant lastExecutionTime = Instant.EPOCH;
    private long currentPeriodSeconds;
    private boolean isAlertSent;

    public SchedulerService(
            KatyaGlucoBot katyaGlucoBot,
            ReadingService readingService,
            SchedulerControls schedulerControls,
            Long periodAccelerated,
            Long periodDefault,
            Long periodAlert) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.readingService = readingService;
        this.schedulerControls = schedulerControls;
        this.periodAccelerated = periodAccelerated;
        this.periodDefault = periodDefault;
        this.periodAlert = periodAlert;
        this.currentPeriodSeconds = this.periodDefault;
    }

    /**
     * Updates the scheduler period if updated period value is provided.
     *
     * @param changeStatus The ChangeStatus to determine the period
     */
    private void updateSchedulerPeriod(ChangeStatus changeStatus) {
        schedulerControls.getUpdatedSchedulerPeriod(changeStatus, currentPeriodSeconds)
                .ifPresent(newPeriod -> currentPeriodSeconds = newPeriod);
    }

    /**
     * Checks if the scheduler period needs to be accelerated based on the last reading time.
     * If the last reading is older than the default period, switches to an accelerated period.
     *
     * @param lastReadingOpt Optional containing the last reading
     * @param currentTime    current time to compare against
     */
    private void accelerateSchedulerIfReadingOutdated(Optional<XDripReading> lastReadingOpt, Instant currentTime) {
        schedulerControls.isLastReadingTimeExpired(lastReadingOpt, currentTime, periodDefault)
                .ifPresent(time -> {
                    LOG.info("Forcing accelerated scheduler period due to last reading being older than {}", periodDefault);
                    currentPeriodSeconds = periodAccelerated;
                });
    }

    /**
     * Tries to send an alert message.
     *
     * @param lastReadingOpt Optional containing the last reading
     * @param currentTime    current time to compare against
     */
    private void trySendAlert(final Optional<XDripReading> lastReadingOpt, final Instant currentTime) {
        if (schedulerControls.shouldSendAlert(isAlertSent, lastReadingOpt, currentTime)) {
            LOG.info("It's time to send the alert message as it's hasn't been sent and the last reading being older than {}",
                    periodAlert);
            katyaGlucoBot.sendSensorReadingUpdateToAll(TextMessageFormatter.formatAlert(lastReadingOpt, currentTime));
            isAlertSent = true;
        }
    }

    @Scheduled(fixedDelayString = "${scheduler.every}")
    public void updateReadings() {
        Instant now = Instant.now();
        long secondsSinceLastExecution = now.getEpochSecond() - lastExecutionTime.getEpochSecond();

        // Only execute if enough time has passed based on the current period
        if (secondsSinceLastExecution < currentPeriodSeconds) {
            LOG.debug("Skipping execution, {} seconds passed out of {} seconds period", secondsSinceLastExecution,
                    currentPeriodSeconds);
            return;
        }
        lastExecutionTime = now;
        LOG.info("Executing scheduled task with period: {}", currentPeriodSeconds);
        var lastReadingOpt = readingService.getLastReading();
        var newReadingOpt = readingService.updateAndReturnLastReadingIfNew(lastReadingOpt);
        if (newReadingOpt.isPresent()) {
            isAlertSent = false;
            var newReading = newReadingOpt.get();
            katyaGlucoBot.sendSensorReadingUpdateToAll(TextMessageFormatter
                    .format(new XDripReadingContext(newReading, TriggerEvent.DEFAULT)));
            updateSchedulerPeriod(newReading.getChangeStatus());
        } else {
            accelerateSchedulerIfReadingOutdated(lastReadingOpt, now);
            trySendAlert(lastReadingOpt, now);
        }
    }
}
