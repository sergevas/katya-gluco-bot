package dev.sergevas.tool.katya.gluco.bot.readings.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.readings.control.ReadingService;
import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.readings.entity.Direction.FLAT;
import static dev.sergevas.tool.katya.gluco.bot.security.AuthenticationService.API_SECRET_HEADER;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReadingsController.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class ReadingsControllerTest {

    @MockitoBean
    private ReadingService readingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenExistentLastReading_whenGet_thenShouldReturnSuccessfully() throws Exception {
        when(readingService.getLastReading()).thenReturn(
                Optional.of(new SensorReading(Instant.parse("2025-04-14T21:07:40.688Z"),
                        7.214927129235995, FLAT)));
        mockMvc.perform(get("/api/v1/readings/last")
                        .header(API_SECRET_HEADER, "test-api-secret"))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "time":"2025-04-14T21:07:40.688Z",
                          "reading":"7.2",
                          "direction":{
                          "name":"FLAT",
                          "mark":"➡"}
                        }
                        """)
                );
    }

    @Test
    void givenNoLastReading_whenGet_thenShouldReturnHttp404() throws Exception {
        when(readingService.getLastReading()).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/readings/last")
                        .header(API_SECRET_HEADER, "test-api-secret"))
                .andExpect(content().contentType(APPLICATION_PROBLEM_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                          "detail":"The last reading is unavailable",
                          "instance":"/api/v1/readings/last",
                          "status":404,
                          "title":"Readings Not Found"
                        }
                        """)
                );
    }
}
