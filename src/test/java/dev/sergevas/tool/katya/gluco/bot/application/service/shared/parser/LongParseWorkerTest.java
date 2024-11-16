package dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongParseWorkerTest {

    @Test
    void unmarshal_timeLocal() {
        // 1729851911 decimal
        byte[] binaryValue = {0x07, 0x72, 0x1b, 0x67};
        assertEquals(1729851911L,
                new LongParseWorker(0, 4).unmarshal(binaryValue));
    }
}
