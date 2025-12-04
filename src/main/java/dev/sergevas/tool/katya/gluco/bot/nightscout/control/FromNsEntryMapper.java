package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import dev.sergevas.tool.katya.gluco.bot.readings.control.SensorReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.readings.entity.ChangeDirection;
import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;

import java.util.List;

public class FromNsEntryMapper implements SensorReadingMapper<List<NsEntry>> {

    @Override
    public List<SensorReading> toSensorReadings(List<NsEntry> nsEntries) {
        return nsEntries.stream()
                .map(this::toSensorReading)
                .toList();
    }

    public SensorReading toSensorReading(NsEntry nsEntry) {
        return new SensorReading(nsEntry.sgvTime().toInstant(), nsEntry.sgvMmolL(), ChangeDirection.fromName(nsEntry.direction()));
    }
}
