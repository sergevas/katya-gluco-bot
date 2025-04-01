package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco;

import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.PollsSensorReadingEntity;
import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.mapper.PollsSensorReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.application.port.out.juggluco.SensorDataRepository;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import dev.sergevas.tool.katya.gluco.bot.infra.log.interceptor.Loggable;
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

    @Inject
    PollsSensorReadingMapper pollsSensorReadingMapper;

    @Loggable
    @Transactional
    @Override
    public void store(List<PollsSensorReading> sensorReadings) {

        final var sensorReadingsByTimeEpoch = sensorReadings.stream()
                .collect(toMap(PollsSensorReading::getTimeEpoch, r -> r));

        Log.debugf("sensorReadingsByTimeEpoch=%s", sensorReadingsByTimeEpoch);

        final var existingPollsSensorReadingsByTimeEpoch =
                em.createNamedQuery("PollsSensorReadingEntity.findByTimeEpoch", PollsSensorReadingEntity.class)
                        .setParameter("timeEpochValues", sensorReadingsByTimeEpoch.keySet())
                        .getResultStream()
                        .collect(toMap(PollsSensorReadingEntity::getTimeEpoch, pollsSensorReadingMapper::toPollsSensorReading));

        Log.debugf("existingPollsSensorReadingsByTimeEpoch=%s", existingPollsSensorReadingsByTimeEpoch);

        var newSensorReadings = sensorReadings.stream()
                .filter(r -> !existingPollsSensorReadingsByTimeEpoch.containsKey(r.getTimeEpoch()))
                .toList();

        Log.debugf("newSensorReadings=%s", newSensorReadings);

        newSensorReadings.stream()
                .map(pollsSensorReadingMapper::toPollsSensorReadingEntity)
                .forEach(em::persist);

        if (!existingPollsSensorReadingsByTimeEpoch.isEmpty()) {
            sensorReadingsByTimeEpoch.forEach((timeToEpoch, reading) -> {
                Optional.ofNullable(existingPollsSensorReadingsByTimeEpoch.get(timeToEpoch)).ifPresent(existingReading -> {
                    if (reading.equals(existingReading)) {
                        Log.warnf("Idempotency conflict occurred: new %s, existing: %s", reading, existingReading);
                    }
                });
            });
        }
    }
}
