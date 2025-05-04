package dev.sergevas.tool.katya.gluco.bot.boundary.scheduler;

import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.control.ReadingService;
import dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.EnumSet;

import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.DOUBLE_DOWN;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.DOUBLE_UP;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.NONE;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.SINGLE_DOWN;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.SINGLE_UP;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.UNDEFINED;

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

        var newReadingOpt = readingService.updateAndReturnLastReadingIfNew();
        if (newReadingOpt.isPresent()) {
            var newReading = newReadingOpt.get();
            katyaGlucoBot.sendSensorReadingUpdate(newReading.toFormattedString());
            updateSchedulerPeriod(newReading.getChangeStatus());
        }
    }
}
