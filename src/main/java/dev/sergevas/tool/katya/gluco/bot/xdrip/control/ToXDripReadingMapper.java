package dev.sergevas.tool.katya.gluco.bot.xdrip.control;

import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.GlucoseData;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.GlucoseMeasurement;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.Result;

import java.util.List;
import java.util.Objects;

public class ToXDripReadingMapper {

    public static final String SERIES_NAME = "glucose";

    public static List<XDripReading> toXDripReadingList(GlucoseData glucoseData) {
        var results = glucoseData.getResults();
        if (Objects.isNull(results) || results.stream()
                .map(Result::getSeries)
                .anyMatch(Objects::isNull)) {
            return List.of();
        }
        return glucoseData.getResults().stream()
                .flatMap(result -> result.getSeries().stream())
                .filter(s -> SERIES_NAME.equals(s.getName()))
                .flatMap(s -> s.getValues().stream())
                .map(GlucoseMeasurement::new)
                .map(ToXDripReadingMapper::toXDripReading)
                .toList();
    }

    public static XDripReading toXDripReading(GlucoseMeasurement gM) {
        return new XDripReading(gM.getTime(), gM.getValueMmol(), ChangeStatus.fromName(gM.getDirection()));
    }
}
