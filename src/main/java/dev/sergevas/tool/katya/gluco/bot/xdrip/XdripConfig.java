package dev.sergevas.tool.katya.gluco.bot.xdrip;

import dev.sergevas.tool.katya.gluco.bot.telegram.control.SensorDataReader;
import dev.sergevas.tool.katya.gluco.bot.xdrip.boundary.InfluxDbServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.FromXDripReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.InfluxDbSensorDataReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "sensor.data.source", havingValue = "xdrip")
public class XdripConfig {

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(RestClient.Builder clientBuilder,
                                                           XdripProperties xdripProperties) {
        var uriBuilderFactory = new DefaultUriBuilderFactory(xdripProperties.url() + "/query?db={db}&q={query}");
        uriBuilderFactory.setDefaultUriVariables(Map.of("query", xdripProperties.query(),
                "db", xdripProperties.db()));

        var clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(5));
        clientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(5));

        var restClient = clientBuilder.baseUrl(xdripProperties.url())
                .uriBuilderFactory(uriBuilderFactory)
                .defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth(xdripProperties.username(), xdripProperties.password()))
                .defaultHeaders(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .requestFactory(clientHttpRequestFactory)
                .build();
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    }

    @Bean
    public FromXDripReadingMapper toXDripReadingMapper() {
        return new FromXDripReadingMapper();
    }

    @Bean
    public InfluxDbServerApiClient influxDbServerApiClient(HttpServiceProxyFactory httpServiceProxyFactory) {
        return httpServiceProxyFactory.createClient(InfluxDbServerApiClient.class);
    }

    @Bean
    public SensorDataReader sensorDataReader(FromXDripReadingMapper fromXDripReadingMapper,
                                             InfluxDbServerApiClient influxDbServerApiClient) {
        return new InfluxDbSensorDataReader(fromXDripReadingMapper, influxDbServerApiClient);
    }
}
