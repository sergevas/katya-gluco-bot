package dev.sergevas.tool.katya.gluco.bot.boundary.influxdb;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class InfluxDbServerApiClient {

    private List<InfluxDbServerApi> influxDbServerApiList;
    private final InfluxDbConfig influxDbConfig;

    public InfluxDbServerApiClient(InfluxDbConfig influxDbConfig) {
        this.influxDbConfig = influxDbConfig;
    }

    @PostConstruct
    public void init() {
        influxDbServerApiList = influxDbConfig.urls().stream()
                .map(url -> RestClientBuilder.newBuilder()
                        .baseUri(URI.create(url))
                        .build(InfluxDbServerApi.class)
                )
                .toList();
    }

    public List<InfluxDbServerApi> getJugglucoWebServerApiList() {
        return influxDbServerApiList;
    }
}
