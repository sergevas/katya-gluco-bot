package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco.parser;

import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PollsSensorDataParserTest {

    private static PollsSensorDataParser parser;

    @BeforeAll
    static void setUp() {
        parser = new PollsSensorDataParser();
        parser.init();
    }

    @Test
    void when_pollsContainNullRows_thenShouldParseSuccessfully() throws IOException {
        var expected = List.of(
                new PollsSensorReading(1729851911L,
                        LocalDateTime.of(2024, 10, 25, 10, 25, 11),
                        101, 54, PollsSensorReading.Trend.FALLING, -1.65f),
                new PollsSensorReading(1729851971L,
                        LocalDateTime.of(2024, 10, 25, 10, 26, 11),
                        102, 53, PollsSensorReading.Trend.FALLING, -1.7f));
        byte[] rawSensorData = Files.readAllBytes(Path.of("src/test/resources/polls01.dat"));
        var pollsSensorReading = parser.parse(rawSensorData);
        assertNotNull(pollsSensorReading);
        assertEquals(expected, pollsSensorReading);
    }

    @Test
    void when_pollsDoesntContainNullRows_thenShouldParseSuccessfully() throws IOException {
        var expected = List.of(
                new PollsSensorReading(1729851911L,
                        LocalDateTime.of(2024, 10, 25, 10, 25, 11),
                        101, 54, PollsSensorReading.Trend.FALLING, -1.65f),
                new PollsSensorReading(1729851971L,
                        LocalDateTime.of(2024, 10, 25, 10, 26, 11),
                        102, 53, PollsSensorReading.Trend.FALLING, -1.7f));
        byte[] rawSensorData = Files.readAllBytes(Path.of("src/test/resources/polls02.dat"));
        var pollsSensorReading = parser.parse(rawSensorData);
        assertNotNull(pollsSensorReading);
        assertEquals(expected, pollsSensorReading);
    }

    @Test
    void isCompleted() {
        assertTrue(parser.isCompleted(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                , (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                , (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}));
    }
}