package dev.sergevas.tool.katya.gluco.bot.telegram;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "telegram")
public record TelegramBotProperties(String botUsername, String token, List<String> chatIds,
                                    Map<String, String> messages) {
}
