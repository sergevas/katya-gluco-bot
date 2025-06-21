package dev.sergevas.tool.katya.gluco.bot.xdrip.entity;

import java.util.Arrays;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

public enum ChangeStatus {

    FLAT("Flat", parseToUnicode(":arrow_right:")),
    SINGLE_DOWN("SingleDown", parseToUnicode(":arrow_down:")),
    DOUBLE_DOWN("DoubleDown", parseToUnicode(":arrow_double_down:")),
    SINGLE_UP("SingleUp", parseToUnicode(":arrow_up:")),
    DOUBLE_UP("DoubleUp", parseToUnicode(":arrow_double_up:")),
    FORTY_FIVE_UP("FortyFiveUp", parseToUnicode(":arrow_upper_right:")),
    FORTY_FIVE_DOWN("FortyFiveDown", parseToUnicode(":arrow_lower_right:")),
    NONE("NONE", parseToUnicode(":confused:")),
    UNDEFINED(null, parseToUnicode(":confused:"));

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
