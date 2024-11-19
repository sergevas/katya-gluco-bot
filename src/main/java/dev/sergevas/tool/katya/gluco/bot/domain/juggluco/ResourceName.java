package dev.sergevas.tool.katya.gluco.bot.domain.juggluco;

import java.util.Objects;

public enum ResourceName {
    UNDEFINED,
    POLLS;

    public static ResourceName fromPath(String path) {
        if (Objects.nonNull(path) && path.toLowerCase().contains("polls")) {
            return POLLS;
        }
        return UNDEFINED;
    }
}
