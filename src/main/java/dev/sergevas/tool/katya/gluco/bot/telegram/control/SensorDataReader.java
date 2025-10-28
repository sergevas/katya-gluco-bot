package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.entity.SensorReading;

import java.util.List;

public interface SensorDataReader {

    List<SensorReading> read();
}
