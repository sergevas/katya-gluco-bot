package dev.sergevas.tool.katya.gluco.bot.recommendation.boundary;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class RecommendationMockProviderTest {

    @Inject
    RecommendationMockProvider recommendationMockProvider;

    @Test
    void givenMealInfo_whenCallMockProvider_thenShouldReturnMockRecommendation() {
        var mealInfo = "162 грамма картофельного пюре с маслом и молоком";
        var mockRecommendation = recommendationMockProvider.provide(mealInfo);
        assertEquals(String.format("На %s нужно сделать ~2,5 ед инсулина (из расчёта 1 ед на 12 г углеводов)", mealInfo),
                mockRecommendation.message());
        assertEquals("Примерный расчёт: 162 г × 15 г углеводов/100 г = ~24 г углеводов → 24 / 12 = 2 ед, но с учётом возможной погрешности лучше округлить до 2,5 ед.",
                mockRecommendation.additionalInfo());
    }
}
