package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.DOUBLE_DOWN;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.DOUBLE_UP;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.NONE;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.SINGLE_DOWN;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.SINGLE_UP;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.UNDEFINED;

public class SchedulerControls {

    private static final EnumSet<ChangeStatus> ACCELERATED_STATUSES = EnumSet.of(
            SINGLE_DOWN, DOUBLE_DOWN, SINGLE_UP, DOUBLE_UP, NONE, UNDEFINED);

    private final Long periodAccelerated;
    private final Long periodDefault;
    private final Long periodAlert;

    public SchedulerControls(Long periodAccelerated, Long periodDefault, Long periodAlert) {
        this.periodAccelerated = periodAccelerated;
        this.periodDefault = periodDefault;
        this.periodAlert = periodAlert;
    }

    public Optional<Instant> isLastReadingTimeExpired(Optional<XDripReading> lastReadingOpt, Instant currentTime,
                                                      Long period) {
        return lastReadingOpt
                .map(XDripReading::getTime)
                .filter(time -> time.isBefore(currentTime.minusSeconds(period)));
    }

    /**
     * Provides the scheduler period based on the ChangeStatus.
     * - If ChangeStatus is SINGLE_DOWN, DOUBLE_DOWN, SINGLE_UP, DOUBLE_UP, NONE or UNDEFINED,
     * the period is set to the {@code  periodAccelerated} s.
     * - If ChangeStatus is FLAT, FORTY_FIVE_UP, or FORTY_FIVE_DOWN, the period is set to {@code  periodDefault} s.
     *
     * @param changeStatus  The ChangeStatus to determine the period
     * @param currentPeriod Current update period
     */
    public Optional<Long> getUpdatedSchedulerPeriod(ChangeStatus changeStatus, Long currentPeriod) {
        if (changeStatus == null) {
            return Optional.empty();
        }
        var newPeriodSeconds = ACCELERATED_STATUSES.contains(changeStatus) ? periodAccelerated : periodDefault;
        return newPeriodSeconds.equals(currentPeriod) ? Optional.empty() : Optional.of(newPeriodSeconds);
    }

    /**
     * Tries to send an alert message.
     *
     * @param isAlertSent    Deduplication flag
     * @param lastReadingOpt Optional containing the last reading
     * @param currentTime    current time to compare against
     */
    public boolean shouldSendAlert(final boolean isAlertSent, final Optional<XDripReading> lastReadingOpt, final Instant currentTime) {
        return !isAlertSent && isLastReadingTimeExpired(lastReadingOpt, currentTime, periodAlert).isPresent();
    }
}
