package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;
import jakarta.ejb.embeddable.EJBContainer;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.DOUBLE_UP;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.FLAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SchedulerControlsTest {

    private static EJBContainer container;

    @Inject
    SchedulerControls schedulerControls;
    @ConfigProperty(name = "scheduler.period.default", defaultValue = "600")
    Long periodDefault;
    @ConfigProperty(name = "scheduler.period.alert", defaultValue = "60")
    Long periodAlert;
    @ConfigProperty(name = "scheduler.period.accelerated", defaultValue = "60")
    Long periodAccelerated;

    @BeforeAll
    public static void start() {
        container = EJBContainer.createEJBContainer();
    }

    @BeforeEach
    public void setUp() throws Exception {
        container.getContext().bind("inject", this);
    }

    @Test
    void givenLastXDripReadingExpired_thenShouldReturnTrue() {
        assertTrue(schedulerControls.isLastReadingTimeExpired(
                Optional.of(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:20:00Z"), periodDefault).isPresent());
        assertTrue(schedulerControls.isLastReadingTimeExpired(
                Optional.of(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:30:00Z"), periodAlert).isPresent());
    }

    @Test
    void givenLastXDripReadingDoesntExpired_thenShouldReturnFalse() {
        assertFalse(schedulerControls.isLastReadingTimeExpired(
                Optional.of(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:10:00Z"), periodDefault).isPresent());
        assertFalse(schedulerControls.isLastReadingTimeExpired(
                Optional.of(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
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
        assertTrue(schedulerControls.shouldSendAlert(false, Optional.of(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:30:00Z")));
    }

    @Test
    void givenAlertSet_whenLastReadingTimeOlderThenAlertPeriod_thenShouldReturnFalse() {
        assertFalse(schedulerControls.shouldSendAlert(true, Optional.of(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:30:00Z")));
    }

    @Test
    void givenAlertNotSet_whenLastReadingTimeNotOlderThenAlertPeriod_thenShouldReturnFalse() {
        assertFalse(schedulerControls.shouldSendAlert(false, Optional.of(new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                7.214927129235995, FLAT)), Instant.parse("2025-04-14T21:20:00Z")));
    }
}
