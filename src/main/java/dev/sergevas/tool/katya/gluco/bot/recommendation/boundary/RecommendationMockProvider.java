package dev.sergevas.tool.katya.gluco.bot.recommendation.boundary;

import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationMessagesConfig;
import dev.sergevas.tool.katya.gluco.bot.recommendation.control.RecommendationProvider;
import dev.sergevas.tool.katya.gluco.bot.recommendation.entity.Recommendation;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@IfBuildProfile("test")
public class RecommendationMockProvider implements RecommendationProvider {

    private final RecommendationMessagesConfig recommendationMessagesConfig;

    public RecommendationMockProvider(RecommendationMessagesConfig recommendationMessagesConfig) {
        this.recommendationMessagesConfig = recommendationMessagesConfig;
    }

    @Override
    public Recommendation provide(String mealInfo) {
        return new Recommendation(String.format(recommendationMessagesConfig.messages().get("mock-message"), mealInfo),
                recommendationMessagesConfig.messages().get("mock-additional-info"));
    }
}
