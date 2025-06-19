package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.entity.XDripReadingContext;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.UNDEFINED;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.X;

public class TextMessageFormatter {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")
            .withZone(ZoneId.ofOffset("UTC", ZoneOffset.of("+03:00")));


    public static String format(XDripReadingContext context) {
        var reading = context.reading();
        return String.format("%s %s", reading.getRounded(), reading.getChangeStatus().getMark());
    }

    public static String formatUpdate(XDripReadingContext context) {
        var reading = context.reading();
        return String.format("%s %s %s%s", reading.getRounded(),
                reading.getChangeStatus().getMark(),
                TIME_FORMATTER.format(reading.getTime()),
                context.triggerEvent().getMark());
    }

    public static String formatAlert(String minutesSinceLastReading) {
        return String.format("%s Нет нового значения, минут: %s",
                X.getMark(),
                Optional.ofNullable(minutesSinceLastReading).orElse(UNDEFINED.getMark()));
    }
}
