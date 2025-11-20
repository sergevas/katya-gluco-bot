package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

public record EntryFilter(String device,
                          String direction,
                          String dateString,
                          String fromDateString,
                          String toDateString) {
}
