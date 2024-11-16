package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco.parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PollsSensorDataParserTest {

    private static PollsSensorDataParser parser;

    @BeforeAll
    static void setUp() {
        parser = new PollsSensorDataParser();
        parser.init();
    }

    @Test
    void parse() throws IOException {
        byte[] rawSensorData = Files.readAllBytes(Path.of("src/test/resources/polls.dat"));
        var pollsSensorReading = parser.parse(rawSensorData);
        assertNotNull(pollsSensorReading);
        assertEquals(2, pollsSensorReading.size());
    }

    @Test
    void isCompleted() {
        assertTrue(parser.isCompleted(new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                , (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                , (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}));
    }
}