package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco;

import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

@QuarkusTest
class SensorDataPersistenceAdapterTest {

    @Inject
    SensorDataPersistenceAdapter sensorDataRepository;

    @Test
    void whenInputSensorReadingsValid_shouldPersist() {
        var sensorReadings = List.of(
                new PollsSensorReading(1729851911L,
                        LocalDateTime.of(2024, 10, 25, 10, 25, 11),
                        101, 54, PollsSensorReading.Trend.FALLING, -1.65f),
                new PollsSensorReading(1729851971L,
                        LocalDateTime.of(2024, 10, 25, 10, 26, 11),
                        102, 53, PollsSensorReading.Trend.FALLING, -1.7f));
        sensorDataRepository.store(sensorReadings);
    }
}
