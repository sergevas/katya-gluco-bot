package dev.sergevas.tool.katya.gluco.bot.readings.control;

import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;

import java.util.List;

public interface SensorDataReader {

    List<SensorReading> read();
}
