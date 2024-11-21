package dev.sergevas.tool.katya.gluco.bot.application.port.out.juggluco;

import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;

import java.util.List;

public interface SensorDataRepository {

    void store(List<PollsSensorReading> sensorReadings);
}
