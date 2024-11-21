package dev.sergevas.tool.katya.gluco.bot.application.port.out.juggluco;

import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource;

import java.util.List;

public interface SensorDataHandler<T> {

    List<T> handle(FileSystemResource fileSystemResource);

}
