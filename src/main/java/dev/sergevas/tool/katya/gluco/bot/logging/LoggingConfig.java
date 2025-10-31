package dev.sergevas.tool.katya.gluco.bot.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    @Bean
    public ApiRequestLoggingFilter apiRequestLoggingFilter() {
        var filter = new ApiRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(20 * 1024 * 1024);
        filter.setIncludeHeaders(true);
        filter.setAfterMessagePrefix("@request: [");
        filter.setIncludeClientInfo(true);
        return filter;
    }
}
