package dev.sergevas.tool.katya.gluco.bot.recommendation.control;

import dev.sergevas.tool.katya.gluco.bot.recommendation.entity.Recommendation;

public interface RecommendationProvider {

    Recommendation provide(String mealInfo);
}
