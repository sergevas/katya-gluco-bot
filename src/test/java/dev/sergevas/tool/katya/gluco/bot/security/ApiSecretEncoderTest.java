package dev.sergevas.tool.katya.gluco.bot.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiSecretEncoderTest {

    @Test
    void encode() {
        assertEquals("e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4", ApiSecretEncoder.encode("secret"));
    }
}
