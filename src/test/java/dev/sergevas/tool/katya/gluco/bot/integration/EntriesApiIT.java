package dev.sergevas.tool.katya.gluco.bot.integration;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest.Entry;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryFilter;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryRepository;
import dev.sergevas.tool.katya.gluco.bot.support.TestContainersConfig;
import dev.sergevas.tool.katya.gluco.bot.support.TestRestClientBuilder;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.SchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static dev.sergevas.tool.katya.gluco.bot.nightscout.support.NightscoutTestData.TEST_REQUEST_1;
import static dev.sergevas.tool.katya.gluco.bot.security.AuthenticationService.API_SECRET_HEADER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(OutputCaptureExtension.class)
@ActiveProfiles("test")
@Import(TestContainersConfig.class)
@Testcontainers
public class EntriesApiIT {

    @MockitoBean
    private KatyaGlucoBot katyaGlucoBot;

    @MockitoBean
    private SchedulerService schedulerService;

    @Autowired
    private PostgreSQLContainer<?> postgreSQLContainer;

    @Autowired
    private NsEntryRepository nsEntryRepository;

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void setUp() throws Exception {
        restClient = TestRestClientBuilder.restClient("http://localhost:" + port + "/api/v1/entries");
    }

    @Test
    void givenAddEntriesPostRequest_whenProcessesSuccessfully_thenShouldPersistNsEntries() {

        restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header(API_SECRET_HEADER, "e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4")
                .body(TEST_REQUEST_1)
                .retrieve()
                .toBodilessEntity();

        var persistedNsEntries = nsEntryRepository.getNsEntries(new NsEntryFilter(
                "3MH01DTCMC4",
                null,
                null,
                null,
                null,
                Pageable.unpaged()
        ));
        assertEquals(2, persistedNsEntries.stream().count());
    }

    @Test
    void givenGetEntriesWoQueryFilterParamsRequest_whenProcessesSuccessfully_thenShouldReturnEntries() {
        var response = restClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .header(API_SECRET_HEADER, "e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4")
                .retrieve()
                .toEntity(Entry.class);
        assertNotNull(response);
    }

    @Test
    void givenAddAlreadyPersistedEntryPostRequest_whenPersist_thenShouldNotLogWarnMessage(CapturedOutput capturedOutput) {
        restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header(API_SECRET_HEADER, "e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4")
                .body(TEST_REQUEST_1)
                .retrieve()
                .toBodilessEntity();
        restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header(API_SECRET_HEADER, "e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4")
                .body(TEST_REQUEST_1)
                .retrieve()
                .toBodilessEntity();
        assertFalse(capturedOutput.getOut().contains("Idempotency conflict occurred"));
    }
}
