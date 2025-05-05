package dev.sergevas.tool.katya.gluco.bot.control;

import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.InfluxDbServerApi;
import dev.sergevas.tool.katya.gluco.bot.entity.XDripReading;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class ReadingService {

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
        Optional<XDripReading> jugglucoStreamReadingOpt = Optional.empty();
        try {
            var glucoseData = influxDbServerApi.getReadings(db, query);
            Objects.requireNonNull(glucoseData, "Glucose Data must not be null!");
            var currentReadings = ToXDripReadingMapper.toXDripReadingList(glucoseData);
            if (!currentReadings.isEmpty()) {
                jugglucoStreamReadingOpt = Optional.of(currentReadings.getLast());
            }
        } catch (Exception e) {
            Log.warn("Unable to fetch a new reading", e);
        }
        return jugglucoStreamReadingOpt;
    }

    public Optional<XDripReading> updateAndReturnLastReading() {
        var lastReadingOpt = getLastReading();
        lastReadingOpt.ifPresent(lastReadingCacheManager::updateReading);
        return lastReadingOpt;
    }

    public Optional<XDripReading> updateAndReturnLastReadingIfNew() {
        return getLastReading().filter(lastReadingCacheManager::checkAndUpdateIfNew);
    }
}
