package dev.sergevas.tool.katya.gluco.bot.xdrip;

import dev.sergevas.tool.katya.gluco.bot.xdrip.boundary.InfluxDbServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.LastReadingCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Map;

@Configuration
public class XdripConfig {

    @Bean
    public LastReadingCacheManager lastReadingCacheManager() {
        return new LastReadingCacheManager();
    }

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(RestClient.Builder clientBuilder,
                                                           XdripProperties xdripProperties) {
        var uriBuilderFactory = new DefaultUriBuilderFactory(xdripProperties.url() + "/query?db={db}&q={query}");
        uriBuilderFactory.setDefaultUriVariables(Map.of("query", xdripProperties.query(),
                "db", xdripProperties.db()));

        var requestFactorySettings = new ClientHttpRequestFactorySettings()

        var restClient = clientBuilder.baseUrl(xdripProperties.url())
                .uriBuilderFactory(uriBuilderFactory)
                .defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth(xdripProperties.username(), xdripProperties.password()))
                .defaultHeaders(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .requestFactory()
                .build();
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    }

    @Bean
    public InfluxDbServerApiClient influxDbServerApiClient(HttpServiceProxyFactory httpServiceProxyFactory) {
        return httpServiceProxyFactory.createClient(InfluxDbServerApiClient.class);
    }
}
