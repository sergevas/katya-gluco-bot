package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")
class NsEntryPersistenceAdapterTest {

    @Test
    void givenNewNsEntries_whenStore_thenShouldStoreAndPublishAppEventForEachStoredNsEntry() {

    }
}