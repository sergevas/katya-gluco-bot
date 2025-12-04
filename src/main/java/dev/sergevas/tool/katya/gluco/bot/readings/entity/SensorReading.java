package dev.sergevas.tool.katya.gluco.bot.readings.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

/**
 * @param reading mmol/L
 */
public record SensorReading(Instant time, double reading, ChangeDirection changeDirection) {

    public String getRounded() {
        return BigDecimal.valueOf(reading)
                .setScale(1, RoundingMode.HALF_UP)
                .toPlainString();
    }
}
