package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.TriggerEvent;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.XDripReadingContext;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.ReadingService;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timeout;
import jakarta.ejb.TimerService;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Instant;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;

@Startup
@Singleton
public class SchedulerService {

    private static final Logger LOG = Logger.getLogger(SchedulerService.class.getName());

    private final KatyaGlucoBot katyaGlucoBot;
    private final ReadingService readingService;
    private final SchedulerControls schedulerControls;
    private final Long periodAccelerated;
    private final Long periodDefault;
    private final Long periodAlert;
    private final Long schedulerEvery;

    private Instant lastExecutionTime = Instant.EPOCH;
    private long currentPeriodSeconds;
    private boolean isAlertSent;

    @Resource
    private TimerService timerService;

    @Inject
    public SchedulerService(
            KatyaGlucoBot katyaGlucoBot,
            ReadingService readingService,
            SchedulerControls schedulerControls,
            @ConfigProperty(name = "scheduler.period.accelerated") Long periodAccelerated,
            @ConfigProperty(name = "scheduler.period.default") Long periodDefault,
            @ConfigProperty(name = "scheduler.period.alert") Long periodAlert,
            @ConfigProperty(name = "scheduler.every") Long schedulerEvery) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.readingService = readingService;
        this.schedulerControls = schedulerControls;
        this.periodAccelerated = periodAccelerated;
        this.periodDefault = periodDefault;
        this.periodAlert = periodAlert;
        this.schedulerEvery = schedulerEvery;
        this.currentPeriodSeconds = this.periodDefault;
    }

    @PostConstruct
    public void init() {
        timerService.createTimer(this.schedulerEvery, String.format("Periodic %s seconds timer", this.schedulerEvery));
    }

    @Timeout
    public void updateReadings() {
        Instant now = Instant.now();
        long secondsSinceLastExecution = now.getEpochSecond() - lastExecutionTime.getEpochSecond();

        // Only execute if enough time has passed based on the current period
        if (secondsSinceLastExecution < currentPeriodSeconds) {
            LOG.log(FINE, "Skipping execution, {0} seconds passed out of {1} seconds period", new Object[]{secondsSinceLastExecution, currentPeriodSeconds});
            return;
        }
        lastExecutionTime = now;
        LOG.log(FINE, "Executing scheduled task with period: {0}s", currentPeriodSeconds);
        var lastReadingOpt = readingService.getLastReading();
        var newReadingOpt = readingService.updateAndReturnLastReadingIfNew(lastReadingOpt);
        if (newReadingOpt.isPresent()) {
            isAlertSent = false;
            var newReading = newReadingOpt.get();
            katyaGlucoBot.sendSensorReadingUpdateToAll(TextMessageFormatter
                    .format(new XDripReadingContext(newReading, TriggerEvent.DEFAULT)));
            updateSchedulerPeriod(newReading.changeStatus());
        } else {
            accelerateSchedulerIfReadingOutdated(lastReadingOpt, now);
            trySendAlert(lastReadingOpt, now);
        }
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
                    LOG.log(INFO, "Forcing accelerated scheduler period due to last reading being older than {0}s", periodDefault);
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
            LOG.log(INFO, "It's time to send the alert message as it's hasn't been sent and the last reading being older than {0}s", periodAlert);
            katyaGlucoBot.sendSensorReadingUpdateToAll(TextMessageFormatter.formatAlert(lastReadingOpt, currentTime));
            isAlertSent = true;
        }
    }
}
