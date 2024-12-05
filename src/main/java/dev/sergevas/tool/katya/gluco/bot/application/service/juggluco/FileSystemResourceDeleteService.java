package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco;

import dev.sergevas.tool.katya.gluco.bot.application.port.in.juggluco.FileSystemResourceDeleteHandler;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileSystemResourceDeleteService implements FileSystemResourceDeleteHandler {

    @Override
    public void handleDelete(FileSystemResource resource) {
        Log.debugf("Enter handleDelete %s", resource);
        Log.debugf("Exit handleDelete");
    }
}
