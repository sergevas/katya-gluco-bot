package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest.MapperSupport.toOdtFromString;

public class NsEntryFilterMapper {

    public static NsEntryFilter toNsEntryFilter(EntryFilter entryFilter, Pageable pageable) {
        return new NsEntryFilter(
                entryFilter.device(),
                entryFilter.direction(),
                toOdtFromString(entryFilter.dateString()),
                toOdtFromString(entryFilter.fromDateString()),
                toOdtFromString(entryFilter.toDateString()),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), SortFieldMapper.map(pageable.getSort()))
        );
    }
}
