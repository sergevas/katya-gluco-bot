package dev.sergevas.tool.katya.gluco.bot.domain.filewatch;

public record FileSystemResource(Type type, String path, byte[] content) {

    public enum Type {
        FILE, DIRECTORY;
    }
}
