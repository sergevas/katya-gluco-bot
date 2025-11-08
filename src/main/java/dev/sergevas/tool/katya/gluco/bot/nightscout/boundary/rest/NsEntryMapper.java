package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest.MapperSupport.toOdtFromString;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class NsEntryMapper extends RepresentationModelAssemblerSupport<NsEntry, Entry> {

    public NsEntryMapper() {
        super(NsEntry.class, Entry.class);
    }

    public NsEntry toNsEntry(Entry entry) {
        return new NsEntry(
                entry.getId(),
                entry.getType(),
                entry.getDevice(),
                toOdtFromString(entry.getDateString()),
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

    public List<NsEntry> toNsEntries(List<Entry> entries) {
        return entries.stream()
                .map(this::toNsEntry)
                .toList();
    }

    @Override
    public Entry toModel(NsEntry nsEntry) {
        var entry = new Entry(
                nsEntry.id(),
                nsEntry.type(),
                nsEntry.device(),
                Optional.ofNullable(nsEntry.sgvTime())
                        .map(OffsetDateTime::toString)
                        .orElse(null),
                nsEntry.epochTime(),
                nsEntry.sgv(),
                nsEntry.delta(),
                nsEntry.direction(),
                nsEntry.noise(),
                nsEntry.filtered(),
                nsEntry.unfiltered(),
                nsEntry.rssi()
        );
        entry.add(linkTo(methodOn(EntriesApi.class).getEntryById(nsEntry.id())).withSelfRel());
        return entry;
    }

    public List<Entry> toEntries(List<NsEntry> nsEntries) {
        return nsEntries.stream()
                .map(this::toModel)
                .toList();
    }
}
