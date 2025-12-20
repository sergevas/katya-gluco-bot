package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import dev.sergevas.tool.katya.gluco.bot.AppProperties;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent.NsEntryAppEventListener;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent.NsEntryAppEventPublisher;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryFilter;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryPublishedUseCase;
import dev.sergevas.tool.katya.gluco.bot.support.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import static dev.sergevas.tool.katya.gluco.bot.nightscout.support.NightscoutTestData.NS_ENTRIES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@EnableConfigurationProperties(AppProperties.class)
@Import({
        TestContainersConfig.class,
        NsEntryPersistenceAdapter.class,
        NsEntryAppEventPublisher.class,
        NsEntryAppEventListener.class
})
@Testcontainers
@ActiveProfiles("test")
class NsEntryPersistenceAdapterTest {

    @Autowired
    private PostgreSQLContainer<?> postgreSQLContainer;

    @Autowired
    private NsEntryPersistenceAdapter nsEntryPersistenceAdapter;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @MockitoBean
    private NsEntryPublishedUseCase nsEntryPublishedUseCase;

    @Autowired
    private NsEntryAppEventListener nsEntryAppEventListener;

    @Test
    void givenNewNsEntries_whenStore_thenShouldStoreAndPublishAppEventForEachStoredNsEntry() {
        nsEntryPersistenceAdapter.storeNsEntries(NS_ENTRIES);
        var storedEntries = nsEntryPersistenceAdapter
                .getNsEntries(NsEntryFilter.defaultFilter()).getContent();
        assertEquals(2, storedEntries.size());
        Awaitility.await().atMost(2, SECONDS).untilAsserted(() ->
                verify(nsEntryPublishedUseCase, times(2)).handle(org.mockito.Mockito.any()));
    }
}
