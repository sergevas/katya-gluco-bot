package dev.sergevas.tool.katya.gluco.bot.application.port.in.filewatcher;

import dev.sergevas.tool.katya.gluco.bot.domain.filewatch.FileSystemResource;

public interface ResourceUpdateHandler {

    void handleUpdate(FileSystemResource resource);
}