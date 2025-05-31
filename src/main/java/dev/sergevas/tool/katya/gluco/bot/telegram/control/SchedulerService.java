package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.TriggerEvent;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.XDripReadingContext;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.ReadingService;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.DOUBLE_DOWN;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.DOUBLE_UP;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.NONE;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.SINGLE_DOWN;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.SINGLE_UP;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.UNDEFINED;

@ApplicationScoped
public class SchedulerService {

    private static final long DEFAULT_PERIOD_SECONDS = 600;
    private static final long ACCELERATED_PERIOD_SECONDS = 60;
    private static final EnumSet<ChangeStatus> ACCELERATED_STATUSES = EnumSet.of(
            SINGLE_DOWN, DOUBLE_DOWN, SINGLE_UP, DOUBLE_UP, NONE, UNDEFINED);

    private final KatyaGlucoBot katyaGlucoBot;
    private final ReadingService readingService;

    private Instant lastExecutionTime = Instant.EPOCH;
    private long currentPeriodSeconds = DEFAULT_PERIOD_SECONDS;


    public SchedulerService(KatyaGlucoBot katyaGlucoBot, ReadingService readingService) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.readingService = readingService;
    }

    /**
     * Updates the scheduler period based on the ChangeStatus.
     * - If ChangeStatus is SINGLE_DOWN, DOUBLE_DOWN, SINGLE_UP, DOUBLE_UP, NONE, or UNDEFINED,
     * the period is set to the 60s.
     * - If ChangeStatus is FLAT, FORTY_FIVE_UP, or FORTY_FIVE_DOWN, the period is set to 600s.
     *
     * @param changeStatus The ChangeStatus to determine the period
     */
    private void updateSchedulerPeriod(ChangeStatus changeStatus) {
        if (changeStatus == null) {
            return;
        }

        long newPeriodSeconds = ACCELERATED_STATUSES.contains(changeStatus)
                ? ACCELERATED_PERIOD_SECONDS
                : DEFAULT_PERIOD_SECONDS;

        if (newPeriodSeconds != currentPeriodSeconds) {
            Log.infof("Changing scheduler period from %ds to %ds due to ChangeStatus: %s",
                    currentPeriodSeconds, newPeriodSeconds, changeStatus);
            currentPeriodSeconds = newPeriodSeconds;
        }
    }

    /**
     * Checks if the scheduler period needs to be accelerated based on the last reading time.
     * If the last reading is older than the default period, switches to an accelerated period.
     *
     * @param lastReadingOpt Optional containing the last reading
     * @param currentTime    current time to compare against
     */
    private void accelerateSchedulerIfReadingOutdated(Optional<XDripReading> lastReadingOpt, Instant currentTime) {
        lastReadingOpt
                .map(XDripReading::getTime)
                .filter(time -> time.isBefore(currentTime.minusSeconds(DEFAULT_PERIOD_SECONDS)))
                .ifPresent(time -> {
                    Log.infof("Forcing accelerated scheduler period due to last reading being older than %ds", DEFAULT_PERIOD_SECONDS);
                    currentPeriodSeconds = ACCELERATED_PERIOD_SECONDS;
                });
    }


    @Scheduled(every = "60s")
    public void updateReadings() {
        Instant now = Instant.now();
        long secondsSinceLastExecution = now.getEpochSecond() - lastExecutionTime.getEpochSecond();

        // Only execute if enough time has passed based on the current period
        if (secondsSinceLastExecution < currentPeriodSeconds) {
            Log.debugf("Skipping execution, %d seconds passed out of %d seconds period",
                    secondsSinceLastExecution, currentPeriodSeconds);
            return;
        }
        lastExecutionTime = now;
        Log.infof("Executing scheduled task with period: %ds", currentPeriodSeconds);
        var lastReadingOpt = readingService.getLastReading();
        var newReadingOpt = readingService.updateAndReturnLastReadingIfNew(lastReadingOpt);
        if (newReadingOpt.isPresent()) {
            var newReading = newReadingOpt.get();
            katyaGlucoBot.sendSensorReadingUpdateToAll(TextMessageFormatter
                    .format(new XDripReadingContext(newReading, TriggerEvent.DEFAULT)));
            updateSchedulerPeriod(newReading.getChangeStatus());
        } else {
            accelerateSchedulerIfReadingOutdated(lastReadingOpt, now);
        }
    }
}
