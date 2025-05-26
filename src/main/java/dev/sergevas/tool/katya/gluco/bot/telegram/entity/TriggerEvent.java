package dev.sergevas.tool.katya.gluco.bot.telegram.entity;

public enum TriggerEvent {

    TIMER(" \u1D40"), UPDATE(" \u1D41"), DEFAULT("");

    private final String mark;

    TriggerEvent(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }
}
