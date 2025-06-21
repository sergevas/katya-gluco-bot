package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.FLAT;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class SchedulerControlsTest {

    @Inject
    SchedulerControls schedulerControls;
    @ConfigProperty(name = "scheduler.period.default")
    Long periodDefault;
    @ConfigProperty(name = "scheduler.period.alert")
    Long periodAlert;

    @Test
    void givenLastXDripReadingExpired_thenShouldReturnTrue() {
        assertTrue(schedulerControls.isLastReadingTimeExpired(
                Optional.of(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:20:00Z"), periodDefault).isPresent());
        assertTrue(schedulerControls.isLastReadingTimeExpired(
                Optional.of(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:30:00Z"), periodAlert).isPresent());
    }
}
