package dev.sergevas.tool.katya.gluco.bot.recommendation;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "recommendation")
public record RecommendationMessagesProperties(Map<String, String> messages) {
}
