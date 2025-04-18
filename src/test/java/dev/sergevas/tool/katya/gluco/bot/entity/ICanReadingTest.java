package dev.sergevas.tool.katya.gluco.bot.entity;

import com.vdurmont.emoji.EmojiParser;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.FLAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ICanReadingTest {

    @Test
    void toFormattedString() {
        var iCanReading = new ICanReading(Instant.parse("2025-04-14T21:07:40.688Z"), 7.214927129235995, FLAT);
        assertEquals("7.2 " + EmojiParser.parseToUnicode(":arrow_right:"), iCanReading.toFormattedString());
    }
}
