package dev.sergevas.tool.katya.gluco.bot.config;

import dev.sergevas.tool.katya.gluco.bot.xdrip.control.LastReadingCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class XdripConfig {

    @Bean
    public LastReadingCacheManager lastReadingCacheManager() {
        return new LastReadingCacheManager();
    }

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(WebClient webClientBuilder) {
    }
}
