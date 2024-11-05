package dev.sergevas.tool.katya.gluco.bot.domain.filewatch;

import java.util.Arrays;
import java.util.Objects;

public class FileSystemResource {

    enum Type {
        FILE, DIRECTORY;
    }

    private final Type type;
    private final String path;
    private final byte[] content;

    public FileSystemResource(Type type, String path, byte[] content) {
        this.type = type;
        this.path = path;
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public String getPath() {
        return path;
    }
    public byte[] getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSystemResource that = (FileSystemResource) o;
        return type == that.type && Objects.equals(path, that.path) && Objects.deepEquals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, path, Arrays.hashCode(content));
    }

    @Override
    public String toString() {
        return "FileSystemResource{" +
                "type=" + type +
                ", path='" + path + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
