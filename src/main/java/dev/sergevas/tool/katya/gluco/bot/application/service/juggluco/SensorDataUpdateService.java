package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco;

import dev.sergevas.tool.katya.gluco.bot.application.port.in.juggluco.SensorDataUpdateHandler;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SensorDataUpdateService implements SensorDataUpdateHandler {

    @Override
    public void handleUpdate(FileSystemResource resource) {
        Log.infof("########## Handle file system resource update: %s", resource);
    }
}
