package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.mapper;

import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.Trend.FALLING;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class PollsSensorReadingMapperTest {

    @Inject
    PollsSensorReadingMapper mapper;

    @Test
    void whenInputValid_shouldReturnConvertedEntity() {
        var entity = mapper.toPollsSensorReadingEntity(new PollsSensorReading(
                LocalDateTime.of(2024, 10, 25, 10, 25, 11),
                101, 54, PollsSensorReading.Trend.FALLING, -1.65f));
        assertEquals(LocalDateTime.parse("2024-10-25T10:25:11"), entity.getTimeLocal());
        assertEquals(101, entity.getMinSinceStart());
        assertEquals(54, entity.getGlucose());
        assertEquals(FALLING, entity.getTrend());
        assertEquals(-1.65f, entity.getRateOfChange());
    }
}