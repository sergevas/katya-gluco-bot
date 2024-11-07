package dev.sergevas.tool.katya.gluco.bot.application.service.filewatcher;

import dev.sergevas.tool.katya.gluco.bot.application.port.in.filewatcher.ResourceUpdateHandler;
import dev.sergevas.tool.katya.gluco.bot.domain.filewatch.FileSystemResource;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResourceUpdateService implements ResourceUpdateHandler {

    @Override
    public void handleUpdate(FileSystemResource resource) {
        Log.infof("########## Handle file system resource update: %s", resource);
    }
}
