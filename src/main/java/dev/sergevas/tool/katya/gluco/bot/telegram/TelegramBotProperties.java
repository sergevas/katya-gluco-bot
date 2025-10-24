package dev.sergevas.tool.katya.gluco.bot.telegram;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "telegram")
@ConfigurationPropertiesScan
public class TelegramBotProperties {

    private String botUsername;
    private String token;
    private List<String> chatIds;
    private Map<String, String> messages;
}
