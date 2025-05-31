package dev.sergevas.tool.katya.gluco.bot.recommendation.control;

import dev.sergevas.tool.katya.gluco.bot.recommendation.entity.UnitRecommendation;

public interface RecommendationProvider {

    UnitRecommendation provide(String mealInfo);
}
