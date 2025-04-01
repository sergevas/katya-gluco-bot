package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.mapper;

import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.PollsSensorReadingEntity;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static dev.sergevas.tool.katya.gluco.bot.support.TestData.POLLS_SENSOR_READING_1;
import static dev.sergevas.tool.katya.gluco.bot.support.TestData.POLLS_SENSOR_READING_ENTITY_1;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class PollsSensorReadingMapperTest {

    @Inject
    PollsSensorReadingMapper mapper;

    @ParameterizedTest
    @MethodSource("provideTestData")
    void whenProvidedValidPollsSensorReading_thenShouldReturnConvertedEntity(PollsSensorReading reading, PollsSensorReadingEntity expectedEntity) {
        assertEquals(expectedEntity, mapper.toPollsSensorReadingEntity(reading));
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    void whenProvidedValidPollsSensorReadingEntity_thenShouldReturnConvertedReading(PollsSensorReading reading, PollsSensorReadingEntity expectedEntity) {
        assertEquals(expectedEntity, mapper.toPollsSensorReadingEntity(reading));
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(Arguments.of(POLLS_SENSOR_READING_1, POLLS_SENSOR_READING_ENTITY_1));
    }
}
