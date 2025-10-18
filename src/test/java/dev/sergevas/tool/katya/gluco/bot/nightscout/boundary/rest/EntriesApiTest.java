package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.SensorDataRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

@QuarkusTest
class EntriesApiTest {

    @TestHTTPEndpoint(EntriesApi.class)
    @TestHTTPResource
    URL entries;

    @InjectMock
    SensorDataRepository sensorDataRepository;

    @Test
    void givenMultipleEntriesList_whenPost_thenShouldCompleteSuccessfully() {
        given().contentType(ContentType.JSON)
                .body("""
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
                        ]""")
                .when().post("/entries").then().statusCode(200);
        verify(sensorDataRepository).storeNsEntries(anyList());
    }

    @Test
    void givenEmptyEntryList_whenPost_thenShouldCompleteSuccessfully() {

    }
}