package dev.sergevas.tool.katya.gluco.bot.nightscout.entity;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NsEntryTest {

    @Test
    void testSgvMmolL() {
        NsEntry entry = new NsEntry(
                null,
                "sgv",
                "device123",
                null,
                0L,
                180,
                0.0,
                "Flat",
                0,
                0,
                0,
                0
        );

        double expectedMmolL = 0.055555555555555552472 * 180;
        assertEquals(expectedMmolL, entry.sgvMmolL(), 1e-9);
    }

    @Test
    void testEquals() {

        var entry1 = new NsEntry(
                null,
                "sgv",
                "3MH01DTCMC4",
                OffsetDateTime.parse("2025-09-01T11:13:59+03:00"),
                1756714439000L,
                83,
                -7.25,
                "FortyFiveDown",
                1,
                83000,
                83000,
                100
        );

        var entry2 = new NsEntry(
                1L,
                "sgv",
                "3MH01DTCMC4",
                OffsetDateTime.parse("2025-09-01T08:13:59Z"),
                1756714439000L,
                83,
                -7.25,
                "FortyFiveDown",
                1,
                83000,
                83000,
                100
        );

        assertEquals(entry1, entry2);
    }
}
