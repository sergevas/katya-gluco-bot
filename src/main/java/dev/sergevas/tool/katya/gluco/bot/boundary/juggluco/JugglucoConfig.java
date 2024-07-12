package dev.sergevas.tool.katya.gluco.bot.boundary.juggluco;

import io.smallrye.config.ConfigMapping;

import java.util.List;

@ConfigMapping(prefix = "juggluco")
public interface JugglucoConfig {

    Webserver webserver();

    interface Webserver {
        List<String> urls();
    }
}
