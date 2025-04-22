package dev.sergevas.tool.katya.gluco.bot.boundary.scheduler;

import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.InfluxDbServerApi;
import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.InfluxDbServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.ToICanReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.KatyaGlucoBotApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.TelegramBotApiConfig;
import dev.sergevas.tool.katya.gluco.bot.control.LastReadingCacheManager;
import dev.sergevas.tool.katya.gluco.bot.entity.ICanReading;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class SchedulerService {

    private final KatyaGlucoBotApiClient katyaGlucoBotApiClient;
    private final InfluxDbServerApiClient influxDbServerApiClient;
    private final LastReadingCacheManager lastReadingCacheManager;
    private final TelegramBotApiConfig telegramBotApiConfig;

    private final String db;
    private final String query;


    public SchedulerService(
            @RestClient
            KatyaGlucoBotApiClient katyaGlucoBotApiClient,
            InfluxDbServerApiClient influxDbServerApiClient,
            LastReadingCacheManager lastReadingCacheManager,
            TelegramBotApiConfig telegramBotApiConfig,
            @ConfigProperty(name = "influxdb.db") String db,
            @ConfigProperty(name = "influxdb.query") String query
    ) {
        this.influxDbServerApiClient = influxDbServerApiClient;
        this.katyaGlucoBotApiClient = katyaGlucoBotApiClient;
        this.lastReadingCacheManager = lastReadingCacheManager;
        this.telegramBotApiConfig = telegramBotApiConfig;
        this.db = db;
        this.query = query;
    }

    @Scheduled(every = "600s")
    public void updateReadings() {
        for (InfluxDbServerApi influxDbServerApi : influxDbServerApiClient.getJugglucoWebServerApiList()) {
            Optional<ICanReading> jugglucoStreamReadingOpt = tryToGetLastJugglucoStreamReading(influxDbServerApi);
            if (jugglucoStreamReadingOpt.isPresent()) {
                var jugglucoStreamReading = jugglucoStreamReadingOpt.get();
                sendUpdate(jugglucoStreamReading.toFormattedString());
                break;
            }
            Log.warn("Will try another URL if it is available");
        }
    }

    public Optional<ICanReading> tryToGetLastJugglucoStreamReading(InfluxDbServerApi influxDbServerApi) {
        Optional<ICanReading> jugglucoStreamReadingOpt = Optional.empty();
        try {
            var glucoseData = influxDbServerApi.getReadings(db, query);
            Objects.requireNonNull(glucoseData, "Glucose Data must not be null!");
            var currentReadings = ToICanReadingMapper.toICanReadingList(glucoseData);
            if (currentReadings.isEmpty()) {
                return jugglucoStreamReadingOpt;
            }
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
            telegramBotApiConfig.chatIds().forEach(chatId -> katyaGlucoBotApiClient
                    .sendUpdate(telegramBotApiConfig.token(), chatId, text));
        } catch (Exception e) {
            Log.error(e);
        }
    }
}
