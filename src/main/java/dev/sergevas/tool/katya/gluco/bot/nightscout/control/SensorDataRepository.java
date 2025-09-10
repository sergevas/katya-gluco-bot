package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;

import java.util.List;

public interface SensorDataRepository {

    void store(List<NsEntry> entries);

    List<NsEntry> readAll();
}
