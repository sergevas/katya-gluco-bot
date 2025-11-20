package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface NsEntryRepository {

    void storeNsEntries(List<NsEntry> entries);

    Page<NsEntry> getNsEntries(NsEntryFilter filter);

    Optional<NsEntry> getLatestNsEntry();

    Optional<NsEntry> getById(Long id);
}
