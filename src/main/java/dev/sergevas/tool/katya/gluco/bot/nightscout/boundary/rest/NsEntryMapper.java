package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;

import java.time.OffsetDateTime;
import java.util.List;

public class NsEntryMapper {

    public static NsEntry toNsEntry(Entry entry) {
        return new NsEntry(
                entry.getType(),
                entry.getDevice(),
                OffsetDateTime.parse(entry.getDateString()),
                entry.getDate(),
                entry.getSgv(),
                entry.getDelta(),
                entry.getDirection(),
                entry.getNoise(),
                entry.getFiltered(),
                entry.getUnfiltered(),
                entry.getRssi()
        );
    }

    public static List<NsEntry> toNsEntries(List<Entry> entries) {
        return entries.stream()
                .map(NsEntryMapper::toNsEntry)
                .toList();
    }
}
