package dev.sergevas.tool.katya.gluco.bot.integration;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryRepository;
import dev.sergevas.tool.katya.gluco.bot.support.TestContainersConfig;
import dev.sergevas.tool.katya.gluco.bot.support.TestRestClientBuilder;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.SchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static dev.sergevas.tool.katya.gluco.bot.security.AuthenticationService.API_SECRET_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Value("${server.ssl.key-store}")
    private String keyStorePath;

    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;

    private RestClient restClient;

    @BeforeEach
    void setUp() throws Exception {
        restClient = TestRestClientBuilder.insecureRestClient(keyStorePath, keyStorePassword,
                "https://localhost:" + port + "/entries");
    }

    @Test
    void givenAddEntriesPostRequest_whenProcessesSuccessfully_thenShouldPersistNsEntries() {

        restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header(API_SECRET_HEADER, "e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4")
                .body(TEST_REQUEST)
                .retrieve()
                .toBodilessEntity();

        var persistedNsEntries = nsEntryRepository.getAllNsEntries();
        assertEquals(2, persistedNsEntries.size());
    }

    private static final String TEST_REQUEST = """
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
}
