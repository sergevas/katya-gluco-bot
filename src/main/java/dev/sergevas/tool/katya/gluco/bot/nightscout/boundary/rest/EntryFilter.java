package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryFilter;

import static dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest.MapperSupport.toOdtFromString;

public record EntryFilter(String device,
                          String direction,
                          String dateString,
                          String fromDateString,
                          String toDateString,
                          String sort,
                          Integer page,
                          Integer size) {

    public NsEntryFilter toNsEntryFilter() {
        return new NsEntryFilter(
                device,
                direction,
                toOdtFromString(dateString),
                toOdtFromString(fromDateString),
                toOdtFromString(toDateString),
                sort,
                page,
                size
        );
    }
}
