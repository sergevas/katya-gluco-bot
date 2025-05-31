package dev.sergevas.tool.katya.gluco.bot.recommendation.entity;

public record Recommendation(String message, String additionalInfo) {

    public String fullMessage() {
        return String.format("%s\n----------\n%s", message, additionalInfo);
    }
}
