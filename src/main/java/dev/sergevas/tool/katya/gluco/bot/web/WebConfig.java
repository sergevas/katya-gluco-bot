package dev.sergevas.tool.katya.gluco.bot.web;

import dev.sergevas.tool.katya.gluco.bot.web.boundary.SortSpecConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public SortSpecConverter sortSpecConverter() {
        return new SortSpecConverter();
    }
}
