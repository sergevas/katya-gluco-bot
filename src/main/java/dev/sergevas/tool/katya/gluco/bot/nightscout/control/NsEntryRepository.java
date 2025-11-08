package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;

import java.util.List;
import java.util.Optional;

public interface NsEntryRepository {

    void storeNsEntries(List<NsEntry> entries);

    List<NsEntry> getAllNsEntries(NsEntryFilter filter);

    Optional<NsEntry> getLatestNsEntry();

    Optional<NsEntry> getById(Long id);
}
