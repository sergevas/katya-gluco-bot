package dev.sergevas.tool.katya.gluco.bot.readings.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.readings.entity.Direction;

import java.time.Instant;

public record SensorReadingDto(Instant time, String reading, Direction direction) {
}
