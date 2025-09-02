package dev.sergevas.tool.katya.gluco.bot.recommendation.boundary;

import dev.sergevas.tool.katya.gluco.bot.recommendation.boundary.aiservice.DeepSeekService;
import dev.sergevas.tool.katya.gluco.bot.recommendation.control.RecommendationProvider;
import dev.sergevas.tool.katya.gluco.bot.recommendation.entity.Recommendation;
//import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;

//@ApplicationScoped
//@IfBuildProfile(anyOf = {"dev", "prod"})
public class RecommendationOpenAiProvider implements RecommendationProvider {

    private final DeepSeekService deepSeekService;

    public RecommendationOpenAiProvider(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @Override
    public Recommendation provide(String mealInfo) {
        return deepSeekService.generateRecommendation(mealInfo);
    }
}
