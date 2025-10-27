package dev.sergevas.tool.katya.gluco.bot.recommendation.boundary;

import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationMessagesProperties;
import dev.sergevas.tool.katya.gluco.bot.recommendation.control.RecommendationProvider;
import dev.sergevas.tool.katya.gluco.bot.recommendation.entity.Recommendation;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
public class RecommendationMockProvider implements RecommendationProvider {

    private final RecommendationMessagesProperties recommendationMessagesProperties;

    public RecommendationMockProvider(RecommendationMessagesProperties recommendationMessagesProperties) {
        this.recommendationMessagesProperties = recommendationMessagesProperties;
    }

    @Override
    public Recommendation provide(String mealInfo) {
        return new Recommendation(String.format(recommendationMessagesProperties.messages().get("mock-message"), mealInfo),
                recommendationMessagesProperties.messages().get("mock-additional-info"));
    }
}
