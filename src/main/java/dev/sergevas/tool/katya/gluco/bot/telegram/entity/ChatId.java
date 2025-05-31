package dev.sergevas.tool.katya.gluco.bot.telegram.entity;

public record ChatId(Long id) {
    public String strId() {
        return String.valueOf(id);
    }
}
