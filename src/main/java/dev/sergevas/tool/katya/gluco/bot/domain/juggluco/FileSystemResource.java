package dev.sergevas.tool.katya.gluco.bot.domain.juggluco;

import dev.sergevas.tool.katya.gluco.bot.domain.DomainException;

import java.nio.file.Path;
import java.util.StringJoiner;

public record FileSystemResource(
        ResourceType resourceType,
        EventType eventType,
        Path path,
        String resourceHash,
        byte[] content) {

    public enum ResourceType {
        FILE, DIRECTORY;

        public static ResourceType fromFlag(boolean isDirectory) {
            return isDirectory ? DIRECTORY : FILE;
        }
    }

    public enum EventType {
        CREATE(true),
        MODIFY(true),
        DELETE(false),
        /* An overflow occurred; some events were lost */
        OVERFLOW(false);

        private final boolean canProvideContent;

        EventType(boolean canProvideContent) {
            this.canProvideContent = canProvideContent;
        }

        public boolean canProvideContent() {
            return canProvideContent;
        }
    }

    public FileSystemResource(ResourceType resourceType, EventType eventType, Path path) {
        this(resourceType, eventType, path, null, null);
    }

    public boolean isDirectory() {
        return resourceType == ResourceType.DIRECTORY;
    }

    public String fileName() {
        if (resourceType == ResourceType.DIRECTORY) {
            throw new DomainException("Not a file: " + path);
        }
        return path.getFileName().toString();
    }

    public String pathString() {
        return String.valueOf(path);
    }

    @Override
    public String toString() {
        var sj = new StringJoiner(", ", FileSystemResource.class.getSimpleName() + "[", "]")
                .add("resourceType=" + resourceType)
                .add("eventType=" + eventType)
                .add("path=" + path)
                .add("resourceHash=" + resourceHash);
        if (!isDirectory()) {
            sj.add("fileName()=" + fileName());
        }
        return sj.toString();
    }
}
