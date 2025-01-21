package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco;

import dev.sergevas.tool.katya.gluco.bot.application.port.out.juggluco.SensorDataHandler;
import dev.sergevas.tool.katya.gluco.bot.application.port.out.juggluco.SensorDataRepository;
import dev.sergevas.tool.katya.gluco.bot.application.service.juggluco.parser.PollsSensorDataParser;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import dev.sergevas.tool.katya.gluco.bot.infra.log.interceptor.Loggable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@ApplicationScoped
@Named("poolsSensorDataHandler")
public class PollsSensorDataHandler implements SensorDataHandler<PollsSensorReading> {

    @Inject
    PollsSensorDataParser pollsSensorDataParser;
    @Inject
    SensorDataRepository sensorDataRepository;

    @Loggable
    @Override
    public List<PollsSensorReading> handle(FileSystemResource fileSystemResource) {
        List<PollsSensorReading> readings = pollsSensorDataParser.parse(fileSystemResource.content());
        sensorDataRepository.store(readings);
        return List.of();
    }
}
