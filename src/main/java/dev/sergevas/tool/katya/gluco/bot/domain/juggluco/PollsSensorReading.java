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

    private LocalDateTime timeLocal; // Reading timestamp, local Time Zone
    private int minSinceStart; // Minutes since sensor start
    private int glucose; // Glucose, mg/dL
    private Trend trend;
    private float rateOfChange;

    public PollsSensorReading(LocalDateTime timeLocal, int minSinceStart, int glucose, Trend trend, float rateOfChange) {
        this.timeLocal = timeLocal;
        this.minSinceStart = minSinceStart;
        this.glucose = glucose;
        this.trend = trend;
        this.rateOfChange = rateOfChange;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollsSensorReading that = (PollsSensorReading) o;
        return minSinceStart == that.minSinceStart && glucose == that.glucose
                && Double.compare(rateOfChange, that.rateOfChange) == 0
                && Objects.equals(timeLocal, that.timeLocal) && trend == that.trend;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeLocal, minSinceStart, glucose, trend, rateOfChange);
    }

    @Override
    public String toString() {
        return "PollsSensorReading{" +
                "timeLocal=" + timeLocal +
                ", minSinceStart=" + minSinceStart +
                ", glucose(mg/dL)=" + glucose +
                ", glucose(mmol/L)=" + getGlucoseMmolL() +
                ", trend=" + trend +
                ", rateOfChange=" + rateOfChange +
                '}';
    }
}
