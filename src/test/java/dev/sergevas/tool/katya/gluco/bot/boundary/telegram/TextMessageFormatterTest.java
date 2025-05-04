package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import com.vdurmont.emoji.EmojiParser;
import dev.sergevas.tool.katya.gluco.bot.entity.TriggerEvent;
import dev.sergevas.tool.katya.gluco.bot.entity.XDripReading;
import dev.sergevas.tool.katya.gluco.bot.entity.XDripReadingContext;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static dev.sergevas.tool.katya.gluco.bot.boundary.telegram.TextMessageFormatter.format;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.FLAT;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.SINGLE_DOWN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TextMessageFormatterTest {

    @Test
    void toFormattedString() {
        assertEquals("7.2 " + EmojiParser.parseToUnicode(":arrow_right:") + " \u1D41",
                format(new XDripReadingContext(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT), TriggerEvent.UPDATE)));
        assertEquals("7.0 " + EmojiParser.parseToUnicode(":arrow_down:") + " \u1D40",
                format(new XDripReadingContext(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.02, SINGLE_DOWN), TriggerEvent.TIMER)));
        assertEquals("7.0 " + EmojiParser.parseToUnicode(":arrow_down:"),
                format(new XDripReadingContext(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.02, SINGLE_DOWN), TriggerEvent.DEFAULT)));
    }
}
