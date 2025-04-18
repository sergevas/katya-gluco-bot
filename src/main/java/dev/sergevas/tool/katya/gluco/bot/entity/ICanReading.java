package dev.sergevas.tool.katya.gluco.bot.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;

public class ICanReading {


    private final Instant time;
    private final double reading; // mmol/L
    private final ChangeStatus changeStatus;

    public ICanReading(Instant time, double reading, ChangeStatus changeStatus) {
        this.time = time;
        this.reading = reading;
        this.changeStatus = changeStatus;
    }

    public Instant getTime() {
        return time;
    }

    public double getReading() {
        return reading;
    }

    public ChangeStatus getChangeStatus() {
        return changeStatus;
    }

    public String getRounded() {
        return BigDecimal.valueOf(reading)
                .setScale(1, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString();
    }

    public String toFormattedString() {
        return String.format("%s %s", getRounded(), changeStatus.getMark());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ICanReading that = (ICanReading) o;
        return Double.compare(reading, that.reading) == 0 && Objects.equals(time, that.time) && changeStatus == that.changeStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, reading, changeStatus);
    }

    @Override
    public String toString() {
        return "ICanReading{" +
                "time=" + time +
                ", reading=" + reading +
                ", changeStatus=" + changeStatus +
                '}';
    }
}
