package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.SensorReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.SensorReading;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus;

import java.util.List;

public class FromNsEntryMapper implements SensorReadingMapper<List<NsEntry>> {

    @Override
    public List<SensorReading> toSensorReadings(List<NsEntry> nsEntries) {
        return nsEntries.stream()
                .map(this::toSensorReading)
                .toList();
    }

    public SensorReading toSensorReading(NsEntry nsEntry) {
        return new SensorReading(nsEntry.sgvTime().toInstant(), nsEntry.sgvMmolL(), ChangeStatus.fromName(nsEntry.direction()));
    }
}
