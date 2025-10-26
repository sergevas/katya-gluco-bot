package dev.sergevas.tool.katya.gluco.bot.xdrip.control;

import dev.sergevas.tool.katya.gluco.bot.xdrip.boundary.InfluxDbServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

public class ReadingService {

    private static final Logger LOG = LoggerFactory.getLogger(ReadingService.class);

    private final InfluxDbServerApiClient influxDbServerApiClient;
    private final LastReadingCacheManager lastReadingCacheManager;

    public ReadingService(InfluxDbServerApiClient influxDbServerApiClient,
                          LastReadingCacheManager lastReadingCacheManager) {
        this.lastReadingCacheManager = lastReadingCacheManager;
        this.influxDbServerApiClient = influxDbServerApiClient;
    }

    public Optional<XDripReading> getLastReading() {
        Optional<XDripReading> xDripReadingOpt = Optional.empty();
        try {
            var glucoseData = influxDbServerApiClient.getReadings();
            Objects.requireNonNull(glucoseData, "Glucose Data must not be null!");
            var currentReadings = ToXDripReadingMapper.toXDripReadingList(glucoseData);
            if (!currentReadings.isEmpty()) {
                xDripReadingOpt = Optional.of(currentReadings.getLast());
            }
        } catch (Exception e) {
            LOG.warn("Unable to fetch a new reading", e);
        }
        return xDripReadingOpt;
    }

    public Optional<XDripReading> updateAndReturnLastReading() {
        var lastReadingOpt = getLastReading();
        lastReadingOpt.ifPresent(lastReadingCacheManager::updateReading);
        return lastReadingOpt;
    }

    public Optional<XDripReading> updateAndReturnLastReadingIfNew(Optional<XDripReading> lastReadingOpt) {
        return lastReadingOpt.filter(lastReadingCacheManager::checkAndUpdateIfNew);
    }
}
