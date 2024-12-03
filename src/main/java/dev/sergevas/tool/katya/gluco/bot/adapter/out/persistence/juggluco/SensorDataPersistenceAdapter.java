package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco;

import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.PollsSensorReadingEntity;
import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.Trend;
import dev.sergevas.tool.katya.gluco.bot.application.port.out.juggluco.SensorDataRepository;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SensorDataPersistenceAdapter implements SensorDataRepository {

    @Inject
    EntityManager em;

    @Transactional
    @Override
    public void store(List<PollsSensorReading> sensorReadings) {
        Log.debugf("Enter store sensorReadings.size() = %d", sensorReadings.size());
        sensorReadings.stream()
                .map(this::toPollsSensorReadingEntity)
                .forEach(em::persist);
        Log.debugf("Exit store sensorReadings.size() = %d", sensorReadings.size());
    }

    public PollsSensorReadingEntity toPollsSensorReadingEntity(PollsSensorReading pollsSensorReading) {
        Log.debug("Enter toPollsSensorReadingEntity()");
        var entity = new PollsSensorReadingEntity();
        entity.setTimeLocal(pollsSensorReading.getTimeLocal());
        entity.setMinSinceStart(pollsSensorReading.getMinSinceStart());
        entity.setGlucose(pollsSensorReading.getGlucoseMgDl());
        entity.setTrend(Trend.valueOf(pollsSensorReading.getTrend().name()));
        entity.setRateOfChange(pollsSensorReading.getRateOfChange());
        Log.debug("Exit toPollsSensorReadingEntity()");
        return entity;
    }
}
