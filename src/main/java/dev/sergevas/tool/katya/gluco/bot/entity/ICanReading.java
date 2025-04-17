package dev.sergevas.tool.katya.gluco.bot.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class ICanReading {


    private final long unixTime;
    private final double reading; // mmol/L
    private final ChangeStatus changeStatus;

    public ICanReading(long unixTime, double reading, ChangeStatus changeStatus) {
        this.unixTime = unixTime;
        this.reading = reading;
        this.changeStatus = changeStatus;
    }

    public long getUnixTime() {
        return unixTime;
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
        return unixTime == that.unixTime && Double.compare(reading, that.reading) == 0 && changeStatus == that.changeStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unixTime, reading, changeStatus);
    }

    @Override
    public String toString() {
        return "ICanReading{" +
                "unixTime=" + unixTime +
                ", reading=" + reading +
                ", changeStatus=" + changeStatus +
                '}';
    }
}
