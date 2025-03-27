package dev.sergevas.tool.katya.gluco.bot.application.service.shared.transform;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransformersTest {

    /*
     * Assuming that this timestamp is in seconds 1729851911L:
     * GMT: Friday, October 25, 2024 10:25:11 AM
     * Your time zone: Friday, October 25, 2024 1:25:11 PM GMT+03:00
     */
    @Test
    void toLocalDateTime() {
        System.out.println(Transformers.toLocalDateTime(1729851911L).toLocalDate());
        assertEquals(LocalDateTime.of(2024, 10, 25, 10, 25, 11),
                Transformers.toLocalDateTime(1729851911L));
    }
}
