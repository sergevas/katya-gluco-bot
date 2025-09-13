package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static io.restassured.RestAssured.given;

@QuarkusTest
class EntriesApiTest {

    @TestHTTPEndpoint(EntriesApi.class)
    @TestHTTPResource
    URL entries;

    @Test
    void givenMultipleEntriesList_whenPut_thenShouldCompleteSuccessfully() {
        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)

    }

    @Test
    void givenSingleEntryList_whenPut_thenShouldCompleteSuccessfully() {

    }
}