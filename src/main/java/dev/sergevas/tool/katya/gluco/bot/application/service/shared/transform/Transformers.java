package dev.sergevas.tool.katya.gluco.bot.application.service.shared.transform;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Transformers {

    public static LocalDateTime toLocalDateTime(Long epochTime) {
        var zoneId = ZoneId.of("GMT");
        return Instant.ofEpochSecond(epochTime).atZone(zoneId).toLocalDateTime();
    }
}
