package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;

public record NsEntryFilter(String device,
                            String direction,
                            OffsetDateTime sgvTimeExact,
                            OffsetDateTime sgvTimeFrom,
                            OffsetDateTime sgvTimeTo,
                            Pageable pageable) {
    public static NsEntryFilter defaultFilter() {
        return new NsEntryFilter(
                null,
                null,
                null,
                null,
                null,
                Pageable.unpaged()
        );
    }
}
