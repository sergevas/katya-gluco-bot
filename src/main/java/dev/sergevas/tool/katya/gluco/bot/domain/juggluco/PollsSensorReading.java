package dev.sergevas.tool.katya.gluco.bot.domain.juggluco;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class PollsSensorReading {

    public enum Trend {

        NOT_DETERMINED(0x00),
        FALLING_QUICKLY(0x01),
        FALLING(0x02),
        STABLE(0x03),
        RISING(0x04),
        RISING_QUICKLY(0x05),
        ERROR(0x06);

        private final Integer code;

        Trend(Integer code) {
            this.code = code;
        }

        public static Trend fromCode(Integer code) {
            return Arrays.stream(Trend.values())
                    .filter(v -> v.code.equals(code))
                    .findFirst()
                    .orElse(NOT_DETERMINED);
        }
    }

    private final Long timeEpoch;
    private final LocalDateTime timeLocal; // Reading timestamp, local Time Zone
    private final int minSinceStart; // Minutes since sensor start
    private final int glucose; // Glucose, mg/dL
    private final Trend trend;
    private final float rateOfChange;

    public PollsSensorReading(long timeEpoch, LocalDateTime timeLocal, int minSinceStart, int glucose, Trend trend, float rateOfChange) {
        this.timeEpoch = timeEpoch;
        this.timeLocal = timeLocal;
        this.minSinceStart = minSinceStart;
        this.glucose = glucose;
        this.trend = trend;
        this.rateOfChange = rateOfChange;
    }

    public Long getTimeEpoch() {
        return timeEpoch;
    }

    public LocalDateTime getTimeLocal() {
        return timeLocal;
    }

    public int getMinSinceStart() {
        return minSinceStart;
    }

    public int getGlucoseMgDl() {
        return glucose;
    }

    public Trend getTrend() {
        return trend;
    }

    public float getRateOfChange() {
        return rateOfChange;
    }

    public double getGlucoseMmolL() {
        return 0.055555555555555552472 * glucose;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PollsSensorReading that = (PollsSensorReading) o;
        return minSinceStart == that.minSinceStart
                && glucose == that.glucose
                && Float.compare(rateOfChange, that.rateOfChange) == 0
                && Objects.equals(timeEpoch, that.timeEpoch)
                && Objects.equals(timeLocal, that.timeLocal)
                && trend == that.trend;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeEpoch, timeLocal, minSinceStart, glucose, trend, rateOfChange);
    }

    @Override
    public String toString() {
        return "PollsSensorReading{" +
                "timeEpoch=" + timeEpoch +
                ", timeLocal=" + timeLocal +
                ", minSinceStart=" + minSinceStart +
                ", glucose(mg/dL)=" + glucose +
                ", glucose(mmol/L)=" + getGlucoseMmolL() +
                ", trend=" + trend +
                ", rateOfChange=" + rateOfChange +
                '}';
    }
}
