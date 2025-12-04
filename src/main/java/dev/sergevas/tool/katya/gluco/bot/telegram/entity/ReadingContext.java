package dev.sergevas.tool.katya.gluco.bot.telegram.entity;

import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;

public record ReadingContext(SensorReading reading, TriggerEvent triggerEvent) {
}
