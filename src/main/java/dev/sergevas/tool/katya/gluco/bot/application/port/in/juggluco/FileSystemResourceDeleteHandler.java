package dev.sergevas.tool.katya.gluco.bot.application.port.in.juggluco;

import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.FileSystemResource;

public interface FileSystemResourceDeleteHandler {

    void handleDelete(FileSystemResource resource);
}
