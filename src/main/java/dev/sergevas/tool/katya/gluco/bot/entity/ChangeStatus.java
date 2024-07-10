package dev.sergevas.tool.katya.gluco.bot.entity;

public enum ChangeStatus {

    STABLE("→"),
    FALLING_QUICKLY("↓"),
    RISING_QUICKLY("↑"),
    RISING("↗"),
    FALLING("↘");

    private final String mark;
    ChangeStatus(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }
}
