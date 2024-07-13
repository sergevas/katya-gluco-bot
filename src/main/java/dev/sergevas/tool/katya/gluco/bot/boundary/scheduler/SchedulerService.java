package dev.sergevas.tool.katya.gluco.bot.boundary.scheduler;

import dev.sergevas.tool.katya.gluco.bot.boundary.csv.CsvDataReader;
import dev.sergevas.tool.katya.gluco.bot.boundary.csv.CsvStreamReading;
import dev.sergevas.tool.katya.gluco.bot.boundary.csv.ToJugglucoStreamReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.boundary.juggluco.JugglucoWebServerApi;
import dev.sergevas.tool.katya.gluco.bot.boundary.juggluco.JugglucoWebServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.KatyaGlucoBotApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.TelegramBotApiConfig;
import dev.sergevas.tool.katya.gluco.bot.control.LastReadingCacheManager;
import dev.sergevas.tool.katya.gluco.bot.entity.JugglucoStreamReading;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Objects;
import java.util.Optional;

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
        for (JugglucoWebServerApi jugglucoWebServerApi : jugglucoWebServerApiClient.getJugglucoWebServerApiList()) {
            Optional<JugglucoStreamReading> jugglucoStreamReadingOpt = tryToGetLastJugglucoStreamReading(jugglucoWebServerApi);
            if (jugglucoStreamReadingOpt.isPresent()) {
                var jugglucoStreamReading = jugglucoStreamReadingOpt.get();
                sendUpdate(jugglucoStreamReading.toFormattedString());
                break;
            }
            Log.warn("Will try another URL if it is available");
        }
    }

    public Optional<JugglucoStreamReading> tryToGetLastJugglucoStreamReading(JugglucoWebServerApi jugglucoWebServerApi) {
        Optional<JugglucoStreamReading> jugglucoStreamReadingOpt = Optional.empty();
        try {
            var rawData = jugglucoWebServerApi.getStream();
            Objects.requireNonNull(rawData, "Raw Data must not be null!");
            var currentCsvStreamReading = csvDataReader.readToCsv(CsvStreamReading.class, rawData);
            var currentReadings = ToJugglucoStreamReadingMapper.toJugglucoStreamReadingList(currentCsvStreamReading);
            var lastReading = currentReadings.getLast();
            Log.debugf("Have got the last reading: %s", lastReading);
            if (lastReadingCacheManager.checkUpdatesJugglucoStreamReading(lastReading)) {
                var newReading = lastReadingCacheManager.getCachedJugglucoStreamReading().toFormattedString();
                Log.infof("********** Have got a new reading: %s", newReading);
                jugglucoStreamReadingOpt = Optional.of(lastReading);
            }
        } catch (Exception e) {
            Log.warn("Unable to fetch a new reading", e);
        }
        return jugglucoStreamReadingOpt;
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
