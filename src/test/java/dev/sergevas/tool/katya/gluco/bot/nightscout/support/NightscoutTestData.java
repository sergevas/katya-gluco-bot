package dev.sergevas.tool.katya.gluco.bot.nightscout.support;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;

import java.time.OffsetDateTime;

public class NightscoutTestData {

    public static final NsEntry NS_ENTRY_1 =
            new NsEntry(
                    null,
                    "sgv",
                    "3MH01DTCMC4",
                    OffsetDateTime.parse("2025-09-01T11:13:59.000+03:00"),
                    1756714439000L,
                    83,
                    -7.25,
                    "FortyFiveDown",
                    1,
                    83000,
                    83000,
                    100
            );

    public static final NsEntry NS_ENTRY_2 =
            new NsEntry(
                    null,
                    "sgv",
                    "3MH01DTCMC4",
                    OffsetDateTime.parse("2025-09-01T11:15:01.000+03:00"),
                    1756714501000L,
                    83,
                    -7.0,
                    "FortyFiveDown",
                    1,
                    83000,
                    83000,
                    100
            );
}
