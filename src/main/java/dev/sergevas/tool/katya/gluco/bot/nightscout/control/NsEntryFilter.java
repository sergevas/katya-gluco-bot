package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import java.time.OffsetDateTime;
import java.util.List;

public record NsEntryFilter(String device,
                            String direction,
                            OffsetDateTime sgvTimeExact,
                            OffsetDateTime sgvTimeFrom,
                            OffsetDateTime sgvTimeTo,
                            List<String> sort,
                            Integer page,
                            Integer size) {
}
