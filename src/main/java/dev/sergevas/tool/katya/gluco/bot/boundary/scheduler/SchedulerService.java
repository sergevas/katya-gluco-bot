package dev.sergevas.tool.katya.gluco.bot.boundary.scheduler;

import dev.sergevas.tool.katya.gluco.bot.boundary.csv.CsvDataReader;
import dev.sergevas.tool.katya.gluco.bot.boundary.csv.CsvStreamReading;
import dev.sergevas.tool.katya.gluco.bot.boundary.csv.ToJugglucoStreamReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.boundary.juggluco.JugglucoWebServerApi;
import dev.sergevas.tool.katya.gluco.bot.boundary.juggluco.JugglucoWebServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.KatyaGlucoBotApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.TelegramBotApiConfig;
import dev.sergevas.tool.katya.gluco.bot.control.LastReadingCacheManager;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Iterator;

@ApplicationScoped
public class SchedulerService {

    private final KatyaGlucoBotApiClient katyaGlucoBotApiClient;
    private final JugglucoWebServerApiClient jugglucoWebServerApiClient;
    private final CsvDataReader csvDataReader;
    private final LastReadingCacheManager lastReadingCacheManager;
    private final TelegramBotApiConfig telegramBotApiConfig;


    public SchedulerService(
            @RestClient
            KatyaGlucoBotApiClient katyaGlucoBotApiClient,
            JugglucoWebServerApiClient jugglucoWebServerApiClient,
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
        tryToGetLastJugglucoStreamReading(jugglucoWebServerApiClient.getJugglucoWebServerApiList().iterator());
    }

    public void tryToGetLastJugglucoStreamReading(Iterator<JugglucoWebServerApi> jugglucoWebServerApiIterator) {
        try {
            while (jugglucoWebServerApiIterator.hasNext()) {
                var jugglucoWebServerApi = jugglucoWebServerApiIterator.next();
                var rawData = jugglucoWebServerApi.getStream();
                var currentCsvStreamReading = csvDataReader.readToCsv(CsvStreamReading.class, rawData);
                var currentReadings = ToJugglucoStreamReadingMapper.toJugglucoStreamReadingList(currentCsvStreamReading);
                var lastReading = currentReadings.getLast();
                Log.debugf("Have got the last reading: %s", lastReading);
                if (lastReadingCacheManager.checkUpdatesJugglucoStreamReading(lastReading)) {
                    var newReading = lastReadingCacheManager.getCachedJugglucoStreamReading().toFormattedString();
                    Log.infof("********** Have got a new reading: %s", newReading);
                    sendUpdate(newReading);
                    break;
                }
            }
        } catch (Exception e) {
            Log.error("Unable to fetch a new reading. Will try another URL if it is available", e);
            tryToGetLastJugglucoStreamReading(jugglucoWebServerApiIterator);
        }
    }

    private void sendUpdate(final String text) {
        try {
            telegramBotApiConfig.chatIds().forEach(chatId -> katyaGlucoBotApiClient.sendUpdate(telegramBotApiConfig.token(),
                    chatId, text));
        } catch (Exception e) {
            Log.error(e);
        }
    }
}
