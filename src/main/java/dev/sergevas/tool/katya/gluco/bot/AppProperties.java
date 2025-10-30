package dev.sergevas.tool.katya.gluco.bot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "app")
public record AppProperties(String sensorDataSource,
                            @DefaultValue Db db) {
    public record Db(String name,
                     String schemaName,
                     @DefaultValue("localhost")
                     String host,
                     @DefaultValue("5432")
                     String port,
                     String username,
                     String password) {
    }
}
