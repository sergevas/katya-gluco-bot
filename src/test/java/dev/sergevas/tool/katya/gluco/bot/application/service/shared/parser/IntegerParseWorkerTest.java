package dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading.Trend.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerParseWorkerTest {

    private static IntegerParseWorker integerParseWorker;

    @BeforeAll
    static void setUp() {
        integerParseWorker = new IntegerParseWorker(0, 4);
    }

    @Test
    void unmarshal_minSinceStart() {
        // 101 decimal
        byte[] binaryValue = {0x65, 0x00, 0x00, 0x00};
        assertEquals(101, integerParseWorker.unmarshal(binaryValue));
    }

    @Test
    void unmarshal_glucose() {
        // 54 decimal
        byte[] binaryValue = {0x36, 0x00, 0x00, 0x00};
        assertEquals(54, integerParseWorker.unmarshal(binaryValue));
    }

    @Test
    void unmarshal_Trend() {
        byte[] notDetermined = {0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] fallingQuickly = {0x01, 0x00, 0x00, 0x00, 0x00};
        byte[] falling = {0x02, 0x00, 0x00, 0x00, 0x00};
        byte[] stable = {0x03, 0x00, 0x00, 0x00, 0x00};
        byte[] rising = {0x04, 0x00, 0x00, 0x00, 0x00};
        byte[] risingQuickly = {0x05, 0x00, 0x00, 0x00, 0x00};
        byte[] error = {0x06, 0x00, 0x00, 0x00, 0x00};
        assertEquals(NOT_DETERMINED, fromCode(integerParseWorker.unmarshal(notDetermined)));
        assertEquals(FALLING_QUICKLY, fromCode(integerParseWorker.unmarshal(fallingQuickly)));
        assertEquals(FALLING, fromCode(integerParseWorker.unmarshal(falling)));
        assertEquals(STABLE, fromCode(integerParseWorker.unmarshal(stable)));
        assertEquals(RISING, fromCode(integerParseWorker.unmarshal(rising)));
        assertEquals(RISING_QUICKLY, fromCode(integerParseWorker.unmarshal(risingQuickly)));
        assertEquals(ERROR, fromCode(integerParseWorker.unmarshal(error)));
    }
}
