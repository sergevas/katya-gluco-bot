package dev.sergevas.tool.katya.gluco.bot.readings.control;

import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;

import java.util.List;

public interface SensorReadingMapper<T> {

    List<SensorReading> toSensorReadings(T sourceGlucoseData);
}
