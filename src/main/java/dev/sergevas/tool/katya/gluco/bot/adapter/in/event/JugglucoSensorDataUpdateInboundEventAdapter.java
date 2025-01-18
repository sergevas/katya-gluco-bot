package dev.sergevas.tool.katya.gluco.bot.adapter.in.event;

import dev.sergevas.tool.katya.gluco.bot.application.port.in.juggluco.FileSystemResourceDeleteHandler;
import dev.sergevas.tool.katya.gluco.bot.application.port.in.juggluco.JugglucoSensorDataUpdateException;
import dev.sergevas.tool.katya.gluco.bot.application.port.in.juggluco.SensorDataUpdateHandler;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource;
import dev.sergevas.tool.katya.gluco.bot.infra.log.interceptor.Loggable;
import io.methvin.watcher.DirectoryChangeEvent;
import io.methvin.watcher.hashing.FileHash;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

import java.nio.file.Files;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource.EventType.valueOf;
import static dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource.ResourceType.DIRECTORY;
import static dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource.ResourceType.FILE;

@ApplicationScoped
public class JugglucoSensorDataUpdateInboundEventAdapter {

    @Inject
    SensorDataUpdateHandler sensorDataUpdateHandler;
    @Inject
    FileSystemResourceDeleteHandler fileResourceDeleteHandler;

    @Loggable(logArguments = true)
    public void onDirectoryChangeEvent(@ObservesAsync final DirectoryChangeEvent directoryChangeEvent) {
        var fsr = toFileSystemResource(directoryChangeEvent);
        switch (fsr.eventType()) {
            case CREATE, MODIFY -> sensorDataUpdateHandler.handleUpdate(fsr);
            case DELETE -> fileResourceDeleteHandler.handleDelete(fsr);
            default -> Log.warnf("Unhandled event type %s for %s", fsr.eventType(), fsr);
        }
    }

    public FileSystemResource toFileSystemResource(final DirectoryChangeEvent dce) {
        try {
            var eventType = valueOf(dce.eventType().name());
            var path = dce.path();
            return dce.isDirectory() ?
                    new FileSystemResource(DIRECTORY, eventType, path)
                    : new FileSystemResource(FILE, eventType, path,
                    Optional.ofNullable(dce.hash()).map(FileHash::asString).orElse(null),
                    eventType.canProvideContent() ? Files.readAllBytes(path) : null);
        } catch (Exception e) {
            var errMsg = "Unable to create FileSystemResource instance";
            Log.error(errMsg, e);
            throw new JugglucoSensorDataUpdateException(errMsg, e);
        }
    }
}
