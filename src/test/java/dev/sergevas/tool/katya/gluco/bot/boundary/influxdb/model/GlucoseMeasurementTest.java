package dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlucoseMeasurementTest {

    @Test
    void whenListOfValueObjectsProvided_thenShouldCreateGlucoseMeasurementInstance() {
        List<Object> values = List.of("2025-04-14T21:07:40.688Z", 2.159, "Flat", 0,
                1, 100, 0, 130, 7.214927129235995);
        GlucoseMeasurement expected = new GlucoseMeasurement();
        expected.setTime(Instant.parse("2025-04-14T21:07:40.688Z"));
        expected.setDelta(2.159);
        expected.setDirection("Flat");
        expected.setFiltered(0);
        expected.setNoise(1);
        expected.setRssi(100);
        expected.setUnfiltered(0);
        expected.setValueMgdl(130);
        expected.setValueMmol(7.214927129235995);
        GlucoseMeasurement actual = new GlucoseMeasurement(values);
        assertEquals(expected, actual);
    }
}
