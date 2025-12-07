package dev.sergevas.tool.katya.gluco.bot.readings.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.readings.entity.Direction;
import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;

import java.time.Instant;

public record SensorReadingDto(Instant time, String reading, DirectionDto direction) {

    public static SensorReadingDto fromEntity(SensorReading sensorReading) {
        return new SensorReadingDto(
                sensorReading.time(),
                sensorReading.getRounded(),
                DirectionDto.fromEntity(sensorReading.direction())
        );
    }

    public record DirectionDto(String name, String mark) {
        public static DirectionDto fromEntity(Direction direction) {
            return new DirectionDto(direction.name(), direction.getMark());
        }
    }
}
