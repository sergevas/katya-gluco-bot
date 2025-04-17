package dev.sergevas.tool.katya.gluco.bot.boundary.influxdb;

import io.smallrye.config.ConfigMapping;

import java.util.List;

@ConfigMapping(prefix = "juggluco")
public interface InfluxDbConfig {

    Webserver webserver();

    interface Webserver {
        List<String> urls();
    }
}
