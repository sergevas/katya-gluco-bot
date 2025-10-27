package dev.sergevas.tool.katya.gluco.bot.recommendation.boundary;

import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationMessagesProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = RecommendationMockProvider.class)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application.properties",
        properties = {
                "recommendation.messages.mock-message=На %s нужно сделать ~2,5 ед инсулина (из расчёта 1 ед на 12 г углеводов)",
                "recommendation.messages.mock-additional-info=Примерный расчёт: 162 г × 15 г углеводов/100 г = ~24 г углеводов → 24 / 12 = 2 ед, но с учётом возможной погрешности лучше округлить до 2,5 ед."
        })
@EnableConfigurationProperties(RecommendationMessagesProperties.class)
class RecommendationMockProviderTest {

    @Autowired
    private RecommendationMockProvider recommendationMockProvider;

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
