package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import dev.sergevas.tool.katya.gluco.bot.web.control.SortSpec;

import java.time.OffsetDateTime;
import java.util.List;

public record NsEntryFilter(String device,
                            String direction,
                            OffsetDateTime sgvTimeExact,
                            OffsetDateTime sgvTimeFrom,
                            OffsetDateTime sgvTimeTo,
                            List<SortSpec> sort,
                            Integer page,
                            Integer size) {
}
