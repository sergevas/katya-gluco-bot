package dev.sergevas.tool.katya.gluco.bot.recommend.boundary;

import io.smallrye.config.ConfigMapping;

import java.util.Map;

@ConfigMapping(prefix = "recommendation")
public interface RecommendationMessagesConfig {

    Map<String, String> messages();
}
