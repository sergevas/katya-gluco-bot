package dev.sergevas.tool.katya.gluco.bot.entity;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JugglucoStreamReadingTest {

    @Test
    void toFormattedString() {
        var jugglucoStreamReading = new JugglucoStreamReading(
                "3MH00Y1F110",
                882,
                1720612291,
                OffsetDateTime.parse("2024-07-10T14:51:31+03:00"),
                13854,
                6.8,
                0.49,
                ChangeStatus.STABLE
        );
        assertEquals("6.8 → 2024-07-10T14:51:31", jugglucoStreamReading.toFormattedString());
    }
}
