package dev.sergevas.tool.katya.gluco.bot.readings.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.readings.entity.ChangeDirection;

import java.time.Instant;

public record SensorReadingDto(Instant time, String reading, ChangeDirection changeDirection) {
}
