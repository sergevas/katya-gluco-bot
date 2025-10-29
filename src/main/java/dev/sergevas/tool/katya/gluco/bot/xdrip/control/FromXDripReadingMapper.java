package dev.sergevas.tool.katya.gluco.bot.xdrip.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.control.SensorReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.SensorReading;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.GlucoseData;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.GlucoseMeasurement;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.Result;

import java.util.List;
import java.util.Objects;

public class FromXDripReadingMapper implements SensorReadingMapper<GlucoseData> {

    public static final String SERIES_NAME = "glucose";

    @Override
    public List<SensorReading> toSensorReadings(GlucoseData glucoseData) {
        var results = glucoseData.results();
        if (Objects.isNull(results) || results.stream()
                .map(Result::getSeries)
                .anyMatch(Objects::isNull)) {
            return List.of();
        }
        return glucoseData.results().stream()
                .flatMap(result -> result.getSeries().stream())
                .filter(s -> SERIES_NAME.equals(s.getName()))
                .flatMap(s -> s.getValues().stream())
                .map(GlucoseMeasurement::new)
                .map(this::toSensorReading)
                .toList();
    }

    public SensorReading toSensorReading(GlucoseMeasurement gM) {
        return new SensorReading(gM.getTime(), gM.getValueMmol(), ChangeStatus.fromName(gM.getDirection()));
    }
}
