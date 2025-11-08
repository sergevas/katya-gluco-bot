package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import java.time.OffsetDateTime;
import java.util.Optional;

public class MapperSupport {

    public static OffsetDateTime toOdtFromString(String dateString) {
        return Optional.ofNullable(dateString)
                .map(OffsetDateTime::parse)
                .orElse(null);
    }
}
