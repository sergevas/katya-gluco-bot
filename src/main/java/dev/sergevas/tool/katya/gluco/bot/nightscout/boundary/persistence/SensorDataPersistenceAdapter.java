package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import dev.sergevas.tool.katya.gluco.bot.infra.logging.Loggable;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.NsEntryEntity;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.mapper.NsEntryEntityMapper;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.SensorDataRepository;
import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@ApplicationScoped
public class SensorDataPersistenceAdapter implements SensorDataRepository {

    @Inject
    EntityManager em;

    @Loggable
    @Transactional
    @Override
    public void storeNsEntries(List<NsEntry> entries) {
        final var sensorReadingsByEpochTime = entries.stream().collect(toMap(NsEntry::epochTime, r -> r));
        Log.debugf("sensorReadingsByEpochTime=%s", sensorReadingsByEpochTime);
        final var existingPollsSensorReadingsByTimeEpoch =
                em.createNamedQuery("NsEntryEntity.findByEpochTime", NsEntryEntity.class)
                        .setParameter("epochTimeValues", sensorReadingsByEpochTime.keySet())
                        .getResultStream()
                        .collect(toMap(NsEntryEntity::getEpochTime, NsEntryEntityMapper::toNsEntry));
        Log.debugf("existingPollsSensorReadingsByTimeEpoch=%s", existingPollsSensorReadingsByTimeEpoch);
        var newSensorReadings = entries.stream()
                .filter(r -> !existingPollsSensorReadingsByTimeEpoch.containsKey(r.epochTime()))
                .toList();

        Log.debugf("newSensorReadings=%s", newSensorReadings);

        newSensorReadings.stream()
                .map(NsEntryEntityMapper::toNsEntryEntity)
                .forEach(em::persist);

        if (!existingPollsSensorReadingsByTimeEpoch.isEmpty()) {
            sensorReadingsByEpochTime.forEach((timeToEpoch, reading) ->
                    Optional.ofNullable(existingPollsSensorReadingsByTimeEpoch.get(timeToEpoch)).ifPresent(existingReading -> {
                        if (!reading.equals(existingReading)) {
                            Log.warnf("Idempotency conflict occurred: new %s, existing: %s", reading, existingReading);
                        }
                    }));
        }
    }

    @Loggable
    @Override
    public List<NsEntry> getAllNsEntries() {
        return em.createNamedQuery("NsEntryEntity.findAll", NsEntryEntity.class)
                .getResultStream()
                .map(NsEntryEntityMapper::toNsEntry)
                .toList();
    }
}
