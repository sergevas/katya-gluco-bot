package dev.sergevas.tool.katya.gluco.bot.recommendation;

import dev.sergevas.tool.katya.gluco.bot.recommendation.boundary.RecommendationMockProvider;
import dev.sergevas.tool.katya.gluco.bot.recommendation.control.RecommendationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RecommendationConfig {

    @Bean("recommendationMockProvider")
    @Profile("local")
    public RecommendationProvider recommendationMockProvider(RecommendationMessagesProperties recommendationMessagesProperties) {
        return new RecommendationMockProvider(recommendationMessagesProperties);
    }
}
