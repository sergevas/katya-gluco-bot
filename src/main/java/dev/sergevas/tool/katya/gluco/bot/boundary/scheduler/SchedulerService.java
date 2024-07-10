package dev.sergevas.tool.katya.gluco.bot.boundary.scheduler;

import dev.sergevas.tool.katya.gluco.bot.boundary.csv.CsvDataReader;
import dev.sergevas.tool.katya.gluco.bot.boundary.csv.CsvStreamReading;
import dev.sergevas.tool.katya.gluco.bot.boundary.csv.ToJugglucoStreamReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.boundary.juggluco.JugglucoWebServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.control.LastReadingCacheManager;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class SchedulerService {

    private final JugglucoWebServerApiClient jugglucoWebServerApiClient;
    private final CsvDataReader csvDataReader;
    private final LastReadingCacheManager lastReadingCacheManager;


    public SchedulerService(
            @RestClient
            JugglucoWebServerApiClient jugglucoWebServerApiClient,
            CsvDataReader csvDataReader,
            LastReadingCacheManager lastReadingCacheManager) {
        this.jugglucoWebServerApiClient = jugglucoWebServerApiClient;
        this.csvDataReader = csvDataReader;
        this.lastReadingCacheManager = lastReadingCacheManager;
    }

    @Scheduled(every = "60s")
    public void updateReadings() {
        var rawData = jugglucoWebServerApiClient.getStream();
        var currentCsvStreamReading = csvDataReader.readToCsv(CsvStreamReading.class, rawData);
        var currentReadings = ToJugglucoStreamReadingMapper.toJugglucoStreamReadingList(currentCsvStreamReading);
        var lastReading = currentReadings.getLast();
        Log.infof("Have got the last reading: %s", lastReading);
        if (lastReadingCacheManager.checkUpdatesJugglucoStreamReading(lastReading)) {
            var cachedReading = lastReadingCacheManager.getCachedJugglucoStreamReading();
            Log.infof("********** Have got a new reading: %s", cachedReading.toFormattedString());
        }
    }
}
