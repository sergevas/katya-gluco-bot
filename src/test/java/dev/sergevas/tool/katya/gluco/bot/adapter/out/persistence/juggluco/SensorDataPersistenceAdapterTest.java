package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco;

import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static dev.sergevas.tool.katya.gluco.bot.support.TestData.POLLS_SENSOR_READING_1;
import static dev.sergevas.tool.katya.gluco.bot.support.TestData.POLLS_SENSOR_READING_2;

@QuarkusTest
class SensorDataPersistenceAdapterTest {

    @Inject
    SensorDataPersistenceAdapter sensorDataRepository;

    @ParameterizedTest
    @MethodSource("provideTestData")
    void whenInputSensorReadingsValid_thenShouldPersist(PollsSensorReading reading1, PollsSensorReading reading2) {
        sensorDataRepository.store(List.of(reading1, reading2));
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(Arguments.of(POLLS_SENSOR_READING_1, POLLS_SENSOR_READING_2));
    }
}
