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

    public static final String TEST_REQUEST = """
            [
                {
                    "type": "sgv",
                        "device": "3MH01DTCMC4",
                    "dateString": "2025-09-01T11:13:59.000+03:00",
                    "date": 1756714439000,
                    "sgv": 83,
                    "delta": -7.25,
                    "direction": "FortyFiveDown",
                    "noise": 1,
                    "filtered": 83000,
                    "unfiltered": 83000,
                    "rssi": 100
                },
                {
                    "type": "sgv",
                    "device": "3MH01DTCMC4",
                    "dateString": "2025-09-01T11:15:01.000+03:00",
                    "date": 1756714501000,
                    "sgv": 83,
                    "delta": -7,
                    "direction": "FortyFiveDown",
                    "noise": 1,
                    "filtered": 83000,
                    "unfiltered": 83000,
                    "rssi": 100
                }
            ]""";

    public static final String ENTRY_HAL_PAGED_REPLY = """
            {
              "_embedded": {
                "entryList": [
                  {
                    "id": null,
                    "type": "sgv",
                    "device": "3MH01DTCMC4",
                    "dateString": "2025-09-01T11:13:59+03:00",
                    "date": 1756714439000,
                    "sgv": 83,
                    "delta": -7.25,
                    "direction": "FortyFiveDown",
                    "noise": 1,
                    "filtered": 83000,
                    "unfiltered": 83000,
                    "rssi": 100,
                    "_links": {
                      "self": {
                        "href": "http://localhost/api/v1/entries/{id}",
                        "templated": true
                      }
                    }
                  },
                  {
                    "id": null,
                    "type": "sgv",
                    "device": "3MH01DTCMC4",
                    "dateString": "2025-09-01T11:15:01+03:00",
                    "date": 1756714501000,
                    "sgv": 83,
                    "delta": -7,
                    "direction": "FortyFiveDown",
                    "noise": 1,
                    "filtered": 83000,
                    "unfiltered": 83000,
                    "rssi": 100,
                    "_links": {
                      "self": {
                        "href": "http://localhost/api/v1/entries/{id}",
                        "templated": true
                      }
                    }
                  }
                ]
              },
              "_links": {
                "self": {
                  "href": "http://localhost/api/v1/entries?page=0&size=2"
                }
              },
              "page": {
                "number": 0,
                "size": 2,
                "totalElements": 2,
                "totalPages": 1
              }
            }
            """;
}
