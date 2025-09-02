package dev.sergevas.tool.katya.gluco.bot.xdrip.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;

/**
 * @param reading mmol/L
 */
public record XDripReading(Instant time, double reading, ChangeStatus changeStatus) {

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
    public String toString() {
        return "XDripReading{" +
                "time=" + time +
                ", reading=" + reading +
                ", changeStatus=" + changeStatus +
                '}';
    }
}
