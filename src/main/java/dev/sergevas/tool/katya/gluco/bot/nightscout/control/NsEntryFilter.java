package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import java.time.OffsetDateTime;

public record NsEntryFilter(String device,
                            String direction,
                            OffsetDateTime sgvTimeExact,
                            OffsetDateTime sgvTimeFrom,
                            OffsetDateTime sgvTimeTo,
                            String sort,
                            Integer page,
                            Integer size) {
}
