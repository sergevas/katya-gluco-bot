package dev.sergevas.tool.katya.gluco.bot.readings.control;

import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

public class ReadingService {

    private static final Logger LOG = LoggerFactory.getLogger(ReadingService.class);

    private final SensorDataReader sensorDataReader;
    private final LastReadingCacheManager lastReadingCacheManager;

    public ReadingService(SensorDataReader sensorDataReader,
                          LastReadingCacheManager lastReadingCacheManager) {
        this.sensorDataReader = sensorDataReader;
        this.lastReadingCacheManager = lastReadingCacheManager;
    }

    public Optional<SensorReading> getLastReading() {
        Optional<SensorReading> sensorReadingOpt = Optional.empty();
        try {
            var sensorReadings = sensorDataReader.read();
            Objects.requireNonNull(sensorReadings, "Sensor readings must not be null!");
            if (!sensorReadings.isEmpty()) {
                sensorReadingOpt = Optional.of(sensorReadings.getLast());
            }
        } catch (Exception e) {
            LOG.warn("Unable to fetch a new reading", e);
        }
        return sensorReadingOpt;
    }

    public Optional<SensorReading> updateAndReturnLastReading() {
        var lastReadingOpt = getLastReading();
        lastReadingOpt.ifPresent(lastReadingCacheManager::updateReading);
        return lastReadingOpt;
    }

    public Optional<SensorReading> updateAndReturnLastReadingIfNew(Optional<SensorReading> lastReadingOpt) {
        return lastReadingOpt.filter(lastReadingCacheManager::checkAndUpdateIfNew);
    }
}
