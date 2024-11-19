package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco;

import dev.sergevas.tool.katya.gluco.bot.application.port.in.juggluco.SensorDataUpdateHandler;
import dev.sergevas.tool.katya.gluco.bot.application.port.out.SensorDataHandler;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.ResourceName;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
public class SensorDataUpdateService implements SensorDataUpdateHandler {

    @Named("poolsSensorDataHandler")
    SensorDataHandler<PollsSensorReading> pollsSensorDataHandler;

    @Override
    public void handleUpdate(FileSystemResource resource) {
        Log.debugf("########## Handle file system resource update: %s", resource);
        switch (ResourceName.fromPath(resource.pathString())) {
            case POLLS -> pollsSensorDataHandler.handle(resource);
            default -> Log.warnf("Unable to infer Resource Name from %s", resource);
        }
    }
}
