package dev.sergevas.tool.katya.gluco.bot.telegram.entity;

import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

/**
 * @param reading mmol/L
 */
public record SensorReading(Instant time, double reading, ChangeStatus changeStatus) {

    public String getRounded() {
        return BigDecimal.valueOf(reading)
                .setScale(1, RoundingMode.HALF_UP)
                .toPlainString();
    }
}
