package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.entity.SensorReading;

import java.util.Optional;

public interface SensorDataReadingUseCase {

    public Optional<SensorReading> getLastReading();

    Optional<SensorReading> updateAndReturnLastReading();

    Optional<SensorReading> updateAndReturnLastReadingIfNew(Optional<SensorReading> lastReadingOpt);
}
