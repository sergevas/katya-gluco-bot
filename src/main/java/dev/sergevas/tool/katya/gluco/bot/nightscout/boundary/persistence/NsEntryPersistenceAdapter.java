package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.NsEntryEntity;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.mapper.NsEntryEntityMapper;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryRepository;
import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

public class NsEntryPersistenceAdapter implements NsEntryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(NsEntryPersistenceAdapter.class);

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
                    Optional.ofNullable(existingPollsSensorReadingsByTimeEpoch.get(timeToEpoch)).ifPresent(existingReading -> {
                        if (!reading.equals(existingReading)) {
                            LOG.warn("Idempotency conflict occurred: new {}, existing: {}", reading, existingReading);
                        }
                    }));
        }
    }

    @Override
    public List<NsEntry> getAllNsEntries() {
        LOG.debug("Enter getAllNsEntries()");
        var nsEntries = nsEntryEntityJpaRepository.findAll()
                .stream()
                .map(NsEntryEntityMapper::toNsEntry)
                .toList();
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
}
