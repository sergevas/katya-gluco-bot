package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco.parser;

public interface SensorDataParser<T> {

    T parse(byte[] rawData);
}
