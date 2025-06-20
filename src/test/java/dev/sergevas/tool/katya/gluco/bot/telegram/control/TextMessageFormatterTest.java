package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import com.vdurmont.emoji.EmojiParser;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.TriggerEvent;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.XDripReadingContext;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.FLAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TextMessageFormatterTest {

    @Test
    void testFormatUpdate() {
        assertEquals("7.2 " + EmojiParser.parseToUnicode(":arrow_right:") + " 00:07 \u1D41",
                TextMessageFormatter.formatUpdate(new XDripReadingContext(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT), TriggerEvent.UPDATE)));
    }

    @Test
    void testFormat() {
        assertEquals("7.2 " + EmojiParser.parseToUnicode(":arrow_right:"),
                TextMessageFormatter.format(new XDripReadingContext(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT), TriggerEvent.DEFAULT)));
    }

    @Test
    void testFormatAlert() {
        assertEquals(EmojiParser.parseToUnicode(":x:") + " Нет нового значения, минут: 20",
                TextMessageFormatter.formatAlert(Optional.of(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:27:40.688Z")));
    }
}
