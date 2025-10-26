package dev.sergevas.tool.katya.gluco.bot.xdrip;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "influxdb")
public record XdripProperties(String url, String username, String password, String db, String query) {
}
