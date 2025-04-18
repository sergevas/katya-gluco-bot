package dev.sergevas.tool.katya.gluco.bot.boundary.influxdb;

import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.model.GlucoseData;
import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.model.GlucoseMeasurement;
import dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.entity.ICanReading;

import java.util.List;

public class ToICanReadingMapper {

    public static final String SERIES_NAME = "glucose";

    public static List<ICanReading> toICanReadingList(GlucoseData glucoseData) {
        return glucoseData.getResults().stream()
                .flatMap(result -> result.getSeries().stream())
                .filter(s -> SERIES_NAME.equals(s.getName()))
                .flatMap(s -> s.getValues().stream())
                .map(GlucoseMeasurement::new)
                .map(ToICanReadingMapper::toICanReading)
                .toList();
    }

    public static ICanReading toICanReading(GlucoseMeasurement gM) {
        return new ICanReading(gM.getTime(), gM.getValueMmol(), ChangeStatus.fromName(gM.getDirection())
        );
    }
}
