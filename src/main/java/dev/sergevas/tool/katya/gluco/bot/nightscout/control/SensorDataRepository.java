package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;

import java.util.List;

public interface SensorDataRepository {

    void storeNsEntries(List<NsEntry> entries);

    List<NsEntry> getAllNsEntries();
}
