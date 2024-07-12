package dev.sergevas.tool.katya.gluco.bot.boundary.juggluco;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class JugglucoWebServerApiClient {

    private List<JugglucoWebServerApi> jugglucoWebServerApiList;
    private final JugglucoConfig jugglucoConfig;

    public JugglucoWebServerApiClient(JugglucoConfig jugglucoConfig) {
        this.jugglucoConfig = jugglucoConfig;
    }

    @PostConstruct
    public void init() {
        jugglucoWebServerApiList = jugglucoConfig.webserver().urls().stream()
                .map(url -> RestClientBuilder.newBuilder().baseUri(URI.create(url))
                        .build(JugglucoWebServerApi.class))
                .toList();
    }

    public List<JugglucoWebServerApi> getJugglucoWebServerApiList() {
        return jugglucoWebServerApiList;
    }
}
