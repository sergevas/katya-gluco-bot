package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.ReadingContext;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;
import static dev.sergevas.tool.katya.gluco.bot.readings.entity.ChangeDirection.UNDEFINED;

public class TextMessageFormatter {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")
            .withZone(ZoneId.ofOffset("UTC", ZoneOffset.of("+03:00")));


    public static String format(ReadingContext context) {
        var reading = context.reading();
        return String.format("%s %s", reading.getRounded(), reading.changeDirection().getMark());
    }

    public static String formatUpdate(ReadingContext context) {
        var reading = context.reading();
        return String.format("%s %s %s%s", reading.getRounded(),
                reading.changeDirection().getMark(),
                TIME_FORMATTER.format(reading.time()),
                context.triggerEvent().getMark());
    }

    public static String formatAlert(Optional<SensorReading> lastReadingOpt, Instant currentTime) {
        var minutesSinceLastReading = lastReadingOpt.map(SensorReading::time)
                .map(t -> Duration.between(t, currentTime))
                .map(Duration::toMinutes)
                .map(String::valueOf)
                .orElse(UNDEFINED.getMark());
        return String.format("%s Нет нового значения, минут: %s", parseToUnicode(":x:"), minutesSinceLastReading);
    }
}
