package dev.sergevas.tool.katya.gluco.bot.xdrip.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;

public class XDripReading {

    private final Instant time;
    private final double reading; // mmol/L
    private final ChangeStatus changeStatus;

    public XDripReading(Instant time, double reading, ChangeStatus changeStatus) {
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
                .toPlainString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        XDripReading that = (XDripReading) o;
        return Double.compare(reading, that.reading) == 0
                && Objects.equals(time, that.time) && changeStatus == that.changeStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, reading, changeStatus);
    }

    @Override
    public String toString() {
        return "XDripReading{" +
                "time=" + time +
                ", reading=" + reading +
                ", changeStatus=" + changeStatus +
                '}';
    }
}
