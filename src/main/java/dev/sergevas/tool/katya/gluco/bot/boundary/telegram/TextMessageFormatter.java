package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import dev.sergevas.tool.katya.gluco.bot.entity.XDripReadingContext;

public class TextMessageFormatter {

    public static String format(XDripReadingContext context) {
        var rounded = context.reading().getRounded();
        var changeStatusMark = context.reading().getChangeStatus().getMark();
        var contextTime = context.reading().getTime();
        var triggerEventMark = context.triggerEvent().getMark();
        return String.format("%s %s %s", rounded, changeStatusMark, triggerEventMark);
    }
}
