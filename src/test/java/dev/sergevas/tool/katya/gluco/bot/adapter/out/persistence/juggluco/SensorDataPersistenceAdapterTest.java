package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco;

import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static dev.sergevas.tool.katya.gluco.bot.support.TestData.POLLS_SENSOR_READING_1;
import static dev.sergevas.tool.katya.gluco.bot.support.TestData.POLLS_SENSOR_READING_1_MODIFIED;
import static dev.sergevas.tool.katya.gluco.bot.support.TestData.POLLS_SENSOR_READING_2;
import static dev.sergevas.tool.katya.gluco.bot.support.TestData.POLLS_SENSOR_READING_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@QuarkusTest
class SensorDataPersistenceAdapterTest {

    @Inject
    SensorDataPersistenceAdapter sensorDataRepository;

    @TestTransaction
    @ParameterizedTest
    @MethodSource("provideAllRecords")
    void givenValidInputSensorReadings_whenStored_thenShouldPersist(PollsSensorReading reading1, PollsSensorReading reading2) {
        sensorDataRepository.store(List.of(reading1, reading2));
    }

    @TestTransaction
    @ParameterizedTest
    @MethodSource("provideAllRecords")
    void givenExistentPersistentEntity_whenReadAll_thenShouldReturnListOfSensorReadings(PollsSensorReading reading1, PollsSensorReading reading2) {
        var records = List.of(reading1, reading2);
        sensorDataRepository.store(records);
        var fetchedRecords = sensorDataRepository.readAll();
        assertIterableEquals(records, fetchedRecords);
    }

    @TestTransaction
    @ParameterizedTest
    @MethodSource("provideDuplicatedRecord")
    void givenIdenticalInputSensorReadings_whenStored_thenShouldNotPersistSecondTime(PollsSensorReading reading1, PollsSensorReading reading2) {
        sensorDataRepository.store(List.of(reading1));
        sensorDataRepository.store(List.of(reading2));
        var storedReadings = sensorDataRepository.readAll();
        assertEquals(1, storedReadings.size());
        assertEquals(reading1, storedReadings.getFirst());
    }

    @TestTransaction
    @ParameterizedTest
    @MethodSource("provideModifiedRecord")
    void givenModifiedPersistentEntity_whenStoreWithSameIdempotencyKey_thenShouldLogWarn(PollsSensorReading reading1,
                                                                                         PollsSensorReading reading1Modified) {
        sensorDataRepository.store(List.of(reading1));
        sensorDataRepository.store(List.of(reading1Modified));
        var storedReadings = sensorDataRepository.readAll();
        assertEquals(1, storedReadings.size());
        assertEquals(reading1, storedReadings.getFirst());
    }

    private static Stream<Arguments> provideAllRecords() {
        return Stream.of(Arguments.of(POLLS_SENSOR_READING_1, POLLS_SENSOR_READING_2, POLLS_SENSOR_READING_3));
    }

    private static Stream<Arguments> provideDuplicatedRecord() {
        return Stream.of(Arguments.of(POLLS_SENSOR_READING_1, POLLS_SENSOR_READING_1));
    }

    private static Stream<Arguments> provideModifiedRecord() {
        return Stream.of(Arguments.of(POLLS_SENSOR_READING_1, POLLS_SENSOR_READING_1_MODIFIED));
    }
}
