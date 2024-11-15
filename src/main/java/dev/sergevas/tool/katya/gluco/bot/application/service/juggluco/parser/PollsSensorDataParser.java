package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco.parser;

import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PollsSensorDataParser implements SensorDataParser<PollsSensorReading> {

    private static final int dataRowLength = 20;

    @Override
    public PollsSensorReading parse(byte[] rawData) {
        return null;
    }
}
