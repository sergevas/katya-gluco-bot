package dev.sergevas.tool.katya.gluco.bot.recommend.control;

import dev.sergevas.tool.katya.gluco.bot.recommend.entity.UnitRecommendation;

public interface RecommendationProvider {

    UnitRecommendation provide(String mealInfo);
}
