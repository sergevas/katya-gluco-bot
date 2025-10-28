package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.entity.SensorReading;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Instant;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.DOUBLE_UP;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.FLAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = SchedulerControlsTest.Config.class)
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
class SchedulerControlsTest {

    @Autowired
    private SchedulerControls schedulerControls;

    @Value("${scheduler.period.default}")
    private Long periodDefault;
    @Value("${scheduler.period.alert}")
    private Long periodAlert;
    @Value("${scheduler.period.accelerated}")
    private Long periodAccelerated;

    @Test
    void givenLastXDripReadingExpired_thenShouldReturnTrue() {
        assertTrue(schedulerControls.isLastReadingTimeExpired(
                Optional.of(new SensorReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:20:00Z"), periodDefault).isPresent());
        assertTrue(schedulerControls.isLastReadingTimeExpired(
                Optional.of(new SensorReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:30:00Z"), periodAlert).isPresent());
    }

    @Test
    void givenLastXDripReadingDoesntExpired_thenShouldReturnFalse() {
        assertFalse(schedulerControls.isLastReadingTimeExpired(
                Optional.of(new SensorReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:10:00Z"), periodDefault).isPresent());
        assertFalse(schedulerControls.isLastReadingTimeExpired(
                Optional.of(new SensorReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:20:00Z"), periodAlert).isPresent());
    }

    @Test
    void givenChangeStatusIsAccelerated_whenDefaultStatus_thenShouldReturnPeriodAccelerated() {
        assertEquals(Optional.of(periodAccelerated), schedulerControls.getUpdatedSchedulerPeriod(DOUBLE_UP, periodDefault));
    }

    @Test
    void givenChangeStatusIsDefault_whenAcceleratedStatus_thenShouldReturnPeriodDefault() {
        assertEquals(Optional.of(periodDefault), schedulerControls.getUpdatedSchedulerPeriod(FLAT, periodAccelerated));
    }

    @Test
    void givenChangeStatusIsAccelerated_whenAcceleratedStatus_thenShouldReturnEmpty() {
        assertEquals(Optional.empty(), schedulerControls.getUpdatedSchedulerPeriod(DOUBLE_UP, periodAccelerated));
    }

    @Test
    void givenChangeStatusIsDefault_whenDefaultStatus_thenShouldReturnEmpty() {
        assertEquals(Optional.empty(), schedulerControls.getUpdatedSchedulerPeriod(FLAT, periodDefault));
    }

    @Test
    void givenAlertNotSet_whenLastReadingTimeOlderThenAlertPeriod_thenShouldReturnTrue() {
        assertTrue(schedulerControls.shouldSendAlert(false, Optional.of(new SensorReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:30:00Z")));
    }

    @Test
    void givenAlertSet_whenLastReadingTimeOlderThenAlertPeriod_thenShouldReturnFalse() {
        assertFalse(schedulerControls.shouldSendAlert(true, Optional.of(new SensorReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:30:00Z")));
    }

    @Test
    void givenAlertNotSet_whenLastReadingTimeNotOlderThenAlertPeriod_thenShouldReturnFalse() {
        assertFalse(schedulerControls.shouldSendAlert(false, Optional.of(new SensorReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:20:00Z")));
    }

    @TestConfiguration
    static class Config {

        @Bean
        public SchedulerControls schedulerControls(@Value("${scheduler.period.accelerated}") Long periodAccelerated,
                                                   @Value("${scheduler.period.default}") Long periodDefault,
                                                   @Value("${scheduler.period.alert}") Long periodAlert) {
            return new SchedulerControls(periodAccelerated, periodDefault, periodAlert);
        }
    }
}
