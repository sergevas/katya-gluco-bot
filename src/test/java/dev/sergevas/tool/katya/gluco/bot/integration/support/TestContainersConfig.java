package dev.sergevas.tool.katya.gluco.bot.integration.support;

import dev.sergevas.tool.katya.gluco.bot.AppProperties;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfig {

    @Bean
    @ServiceConnection
    @RestartScope
    PostgreSQLContainer<?> postgreSQLContainer(AppProperties appProperties) {
        var db = appProperties.db();
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.19-alpine3.21"))
                .withDatabaseName(db.name())
                .withUsername(db.username())
                .withPassword(db.password());
    }
}
