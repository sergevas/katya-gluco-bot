package dev.sergevas.tool.katya.gluco.bot.boundary.influxdb;

import io.smallrye.config.ConfigMapping;

import java.util.List;

@ConfigMapping(prefix = "influxdb.server")
public interface InfluxDbConfig {
    List<String> urls();
}
