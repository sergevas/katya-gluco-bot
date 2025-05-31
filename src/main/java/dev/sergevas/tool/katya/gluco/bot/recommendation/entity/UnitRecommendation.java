package dev.sergevas.tool.katya.gluco.bot.recommendation.entity;

public record UnitRecommendation(String message, String additionalInfo) {

    public String fullMessage() {
        return String.format("%s\n%s", message, additionalInfo);
    }
}
