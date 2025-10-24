package dev.sergevas.tool.katya.gluco.bot.xdrip.control;

import dev.sergevas.tool.katya.gluco.bot.xdrip.boundary.InfluxDbServerApi;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

public class ReadingService {

    private static final Logger LOG = LoggerFactory.getLogger(ReadingService.class);

    private final String db;
    private final String query;
    private final InfluxDbServerApi influxDbServerApi;
    private final LastReadingCacheManager lastReadingCacheManager;

    public ReadingService(
            @ConfigProperty(name = "influxdb.db") String db,
            @ConfigProperty(name = "influxdb.query") String query,
            @RestClient InfluxDbServerApi influxDbServerApi,
            LastReadingCacheManager lastReadingCacheManager) {
        this.db = db;
        this.query = query;
        this.lastReadingCacheManager = lastReadingCacheManager;
        this.influxDbServerApi = influxDbServerApi;
    }

    public Optional<XDripReading> getLastReading() {
        Optional<XDripReading> xDripReadingOpt = Optional.empty();
        try {
            var glucoseData = influxDbServerApi.getReadings(db, query);
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
