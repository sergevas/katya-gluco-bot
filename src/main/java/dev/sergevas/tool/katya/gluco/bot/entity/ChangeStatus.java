package dev.sergevas.tool.katya.gluco.bot.entity;

import java.util.Arrays;

public enum ChangeStatus {

    FLAT("Flat", "→"),
    SINGLE_DOWN("SingleDown", "↓"),
    DOUBLE_DOWN("DoubleDown", "↓"),
    SINGLE_UP("SingleUp", "↑"),
    DOUBLE_UP("DoubleUp", "↑"),
    FORTY_FIVE_UP("FortyFiveUp", "↗"),
    FORTY_FIVE_DOWN("FortyFiveDown", "↘"),
    UNDEFINED(null, "-");

    private final String name;
    private final String mark;

    ChangeStatus(String name, String mark) {
        this.name = name;
        this.mark = mark;
    }

    public static ChangeStatus fromName(String name) {
        return Arrays.stream(ChangeStatus.values())
                .filter(status -> status.name.equals(name))
                .findFirst()
                .orElse(UNDEFINED);
    }

    public String getMark() {
        return mark;
    }
}
