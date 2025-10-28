package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.entity.SensorReading;

import java.util.List;

public interface SensorReadingMapper<T> {

    List<SensorReading> toSensorReadings(T sourceGlucoseData);
}
