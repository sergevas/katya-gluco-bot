package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import dev.sergevas.tool.katya.gluco.bot.readings.control.SensorDataReader;
import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;

import java.util.List;

public class NsEntrySensorDataReader implements SensorDataReader {

    private final FromNsEntryMapper fromNsEntryMapper;
    private final NsEntryRepository nsEntryRepository;

    public NsEntrySensorDataReader(FromNsEntryMapper fromNsEntryMapper, NsEntryRepository nsEntryRepository) {
        this.fromNsEntryMapper = fromNsEntryMapper;
        this.nsEntryRepository = nsEntryRepository;
    }

    @Override
    public List<SensorReading> read() {
        return nsEntryRepository.getLatestNsEntry()
                .map(fromNsEntryMapper::toSensorReading)
                .map(List::of)
                .orElseGet(List::of);
    }
}
