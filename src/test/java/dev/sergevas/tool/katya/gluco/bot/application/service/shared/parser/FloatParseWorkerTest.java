package dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatParseWorkerTest {

    private static FloatParseWorker floatParseWorker;

    @BeforeAll
    static void setUp() {
        floatParseWorker = new FloatParseWorker(0, 4);
    }

    @Test
    void unmarshal_rateOfChange() {
        byte[] value1 = {0x48, (byte) 0xe1, 0x7a, (byte) 0xbf};
        byte[] value2 = {0x71, (byte) 0x3d, (byte) 0xca, (byte) 0xbf};
        byte[] value3 = {0x5c, (byte) 0x8f, 0x42, (byte) 0xc0};
        assertEquals(-0.98f, floatParseWorker.unmarshal(value1));
        assertEquals(-1.58f, floatParseWorker.unmarshal(value2));
        assertEquals(-3.04f, floatParseWorker.unmarshal(value3));
    }
}