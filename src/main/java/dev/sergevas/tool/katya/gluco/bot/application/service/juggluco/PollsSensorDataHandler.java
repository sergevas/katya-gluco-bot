package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco;

import dev.sergevas.tool.katya.gluco.bot.application.port.out.juggluco.SensorDataHandler;
import dev.sergevas.tool.katya.gluco.bot.application.port.out.juggluco.SensorDataRepository;
import dev.sergevas.tool.katya.gluco.bot.application.service.juggluco.parser.PollsSensorDataParser;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import io.quarkus.logging.Log;
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

    @Override
    public List<PollsSensorReading> handle(FileSystemResource fileSystemResource) {
        Log.debug("Enter handle()");
        List<PollsSensorReading> readings = pollsSensorDataParser.parse(fileSystemResource.content());
        sensorDataRepository.store(readings);
        Log.debug("Exit handle()");
        return List.of();
    }
}
