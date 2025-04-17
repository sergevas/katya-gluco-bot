package dev.sergevas.tool.katya.gluco.bot.entity;

import com.vdurmont.emoji.EmojiParser;
import org.junit.jupiter.api.Test;

import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.FLAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ICanReadingTest {

    @Test
    void toFormattedString() {
        var iCanReading = new ICanReading(1744664860688000000L, 7.214927129235995, FLAT);
        assertEquals("7.2 " + EmojiParser.parseToUnicode(":arrow_right:"), iCanReading.toFormattedString());
    }
}
