package dev.sergevas.tool.katya.gluco.bot.boundary.scheduler;

import dev.sergevas.tool.katya.gluco.bot.boundary.csv.CsvDataReader;
import dev.sergevas.tool.katya.gluco.bot.boundary.csv.CsvStreamReading;
import dev.sergevas.tool.katya.gluco.bot.boundary.csv.ToJugglucoStreamReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.boundary.juggluco.JugglucoWebServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.KatyaGlucoBotApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.TelegramBotApiConfig;
import dev.sergevas.tool.katya.gluco.bot.control.LastReadingCacheManager;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class SchedulerService {

    private final JugglucoWebServerApiClient jugglucoWebServerApiClient;
    private final KatyaGlucoBotApiClient katyaGlucoBotApiClient;
    private final CsvDataReader csvDataReader;
    private final LastReadingCacheManager lastReadingCacheManager;
    private final TelegramBotApiConfig telegramBotApiConfig;


    public SchedulerService(
            @RestClient
            JugglucoWebServerApiClient jugglucoWebServerApiClient,
            @RestClient
            KatyaGlucoBotApiClient katyaGlucoBotApiClient,
            CsvDataReader csvDataReader,
            LastReadingCacheManager lastReadingCacheManager,
            TelegramBotApiConfig telegramBotApiConfig
    ) {
        this.jugglucoWebServerApiClient = jugglucoWebServerApiClient;
        this.katyaGlucoBotApiClient = katyaGlucoBotApiClient;
        this.csvDataReader = csvDataReader;
        this.lastReadingCacheManager = lastReadingCacheManager;
        this.telegramBotApiConfig = telegramBotApiConfig;
    }

    @Scheduled(every = "600s")
    public void updateReadings() {
        var rawData = jugglucoWebServerApiClient.getStream();
        var currentCsvStreamReading = csvDataReader.readToCsv(CsvStreamReading.class, rawData);
        var currentReadings = ToJugglucoStreamReadingMapper.toJugglucoStreamReadingList(currentCsvStreamReading);
        var lastReading = currentReadings.getLast();
        Log.debugf("Have got the last reading: %s", lastReading);
        if (lastReadingCacheManager.checkUpdatesJugglucoStreamReading(lastReading)) {
            final var cachedReading = lastReadingCacheManager.getCachedJugglucoStreamReading();
            try {
                telegramBotApiConfig.chatIds().forEach(chatId -> katyaGlucoBotApiClient.sendUpdate(telegramBotApiConfig.token(),
                        chatId, cachedReading.toFormattedString()));
            } catch (Exception e) {
                Log.error(e);
            }
            Log.infof("********** Have got a new reading: %s", cachedReading.toFormattedString());
        }
    }
}
