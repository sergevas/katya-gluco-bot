package dev.sergevas.tool.katya.gluco.bot.recommend.boundary;

import dev.sergevas.tool.katya.gluco.bot.recommend.control.RecommendationProvider;
import dev.sergevas.tool.katya.gluco.bot.recommend.entity.UnitRecommendation;
import io.quarkus.arc.profile.UnlessBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@UnlessBuildProfile("prod")
public class RecommendationMockProvider implements RecommendationProvider {

    private final RecommendationMessagesConfig recommendationMessagesConfig;

    public RecommendationMockProvider(RecommendationMessagesConfig recommendationMessagesConfig) {
        this.recommendationMessagesConfig = recommendationMessagesConfig;
    }

    @Override
    public UnitRecommendation provide(String mealInfo) {
        return new UnitRecommendation(String.format(recommendationMessagesConfig.messages().get("mock-message"), mealInfo),
                recommendationMessagesConfig.messages().get("mock-additional-info"));
    }
}
