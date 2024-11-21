package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco;

import dev.sergevas.tool.katya.gluco.bot.application.port.out.juggluco.SensorDataHandler;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.List;

@ApplicationScoped
@Named("poolsSensorDataHandler")
public class PollsSensorDataHandler implements SensorDataHandler<PollsSensorReading> {

    @Override
    public List<PollsSensorReading> handle(FileSystemResource fileSystemResource) {
        return List.of();
    }
}
