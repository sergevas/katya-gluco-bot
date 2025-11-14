package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.NsEntryEntity;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.mapper.NsEntryEntityMapper;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryFilter;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryRepository;
import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public class NsEntryPersistenceAdapter implements NsEntryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(NsEntryPersistenceAdapter.class);

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final NsEntryEntityJpaRepository nsEntryEntityJpaRepository;

    public NsEntryPersistenceAdapter(NsEntryEntityJpaRepository nsEntryEntityJpaRepository) {
        this.nsEntryEntityJpaRepository = nsEntryEntityJpaRepository;
    }

    @Transactional
    @Override
    public void storeNsEntries(List<NsEntry> entries) {
        final var sensorReadingsByEpochTime = entries.stream().collect(toMap(NsEntry::epochTime, r -> r));
        LOG.debug("sensorReadingsByEpochTime={}", sensorReadingsByEpochTime);
        final var existingPollsSensorReadingsByTimeEpoch =
                nsEntryEntityJpaRepository.findByEpochTime(sensorReadingsByEpochTime.keySet())
                        .stream()
                        .collect(toMap(NsEntryEntity::getEpochTime, NsEntryEntityMapper::toNsEntry));
        LOG.debug("existingPollsSensorReadingsByTimeEpoch={}", existingPollsSensorReadingsByTimeEpoch);
        var newSensorReadings = entries.stream()
                .filter(r -> !existingPollsSensorReadingsByTimeEpoch.containsKey(r.epochTime()))
                .toList();

        LOG.debug("newSensorReadings={}", newSensorReadings);

        newSensorReadings.stream()
                .map(NsEntryEntityMapper::toNsEntryEntity)
                .forEach(nsEntryEntityJpaRepository::save);

        if (!existingPollsSensorReadingsByTimeEpoch.isEmpty()) {
            sensorReadingsByEpochTime.forEach((timeToEpoch, reading) ->
                    ofNullable(existingPollsSensorReadingsByTimeEpoch.get(timeToEpoch)).ifPresent(existingReading -> {
                        if (!reading.equals(existingReading)) {
                            LOG.warn("Idempotency conflict occurred: new {}, existing: {}", reading, existingReading);
                        }
                    }));
        }
    }

    @Override
    public List<NsEntry> getNsEntries(NsEntryFilter filter) {
        LOG.debug("Enter getNsEntries() filter={}", filter);
        var sort = SortMapper.toEntitySort(filter.sort());
        var pageable = PageRequest.of(
                ofNullable(filter.page()).orElse(DEFAULT_PAGE_NUMBER),
                ofNullable(filter.size()).orElse(DEFAULT_PAGE_SIZE),
                sort);
        List<NsEntry> nsEntries;
        try {
            nsEntries = nsEntryEntityJpaRepository.findAll(NsEntryEntitySpecification.toSpecification(filter), pageable)
                    .stream()
                    .map(NsEntryEntityMapper::toNsEntry)
                    .toList();
        } catch (Exception e) {
            LOG.error("Error fetching NsEntries with filter: {} and pageable: {}", filter, pageable, e);
            throw e;
        }
        LOG.debug("Exit getAllNsEntries() nsEntries={}", nsEntries);
        return nsEntries;
    }

    @Override
    public Optional<NsEntry> getLatestNsEntry() {
        LOG.debug("Enter getLatestNsEntry()");
        var nsEntry = nsEntryEntityJpaRepository.findFirstByOrderByEpochTimeDesc()
                .map(NsEntryEntityMapper::toNsEntry);
        LOG.debug("Exit getAllNsEntries() nsEntry={}", nsEntry);
        return nsEntry;
    }

    @Override
    public Optional<NsEntry> getById(Long id) {
        LOG.debug("Enter getById() id={}", id);
        var nsEntry = nsEntryEntityJpaRepository.findFirstByOrderByEpochTimeDesc()
                .map(NsEntryEntityMapper::toNsEntry);
        LOG.debug("Exit getById() nsEntry={}", nsEntry);
        return nsEntry;
    }
}
