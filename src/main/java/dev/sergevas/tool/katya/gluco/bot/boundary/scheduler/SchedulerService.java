package dev.sergevas.tool.katya.gluco.bot.boundary.scheduler;

import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.InfluxDbServerApi;
import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.InfluxDbServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.ToICanReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.KatyaGlucoBotApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.TelegramBotApiConfig;
import dev.sergevas.tool.katya.gluco.bot.control.LastReadingCacheManager;
import dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.entity.ICanReading;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.DOUBLE_DOWN;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.DOUBLE_UP;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.NONE;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.SINGLE_DOWN;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.SINGLE_UP;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.UNDEFINED;

@ApplicationScoped
public class SchedulerService {

    private static final long DEFAULT_PERIOD_SECONDS = 600;
    private static final long ACCELERATED_PERIOD_SECONDS = 60;
    private static final EnumSet<ChangeStatus> ACCELERATED_STATUSES = EnumSet.of(
            SINGLE_DOWN, DOUBLE_DOWN, SINGLE_UP, DOUBLE_UP, NONE, UNDEFINED);

    private final KatyaGlucoBotApiClient katyaGlucoBotApiClient;
    private final InfluxDbServerApiClient influxDbServerApiClient;
    private final LastReadingCacheManager lastReadingCacheManager;
    private final TelegramBotApiConfig telegramBotApiConfig;

    private final String db;
    private final String query;

    private Instant lastExecutionTime = Instant.EPOCH;
    private long currentPeriodSeconds = DEFAULT_PERIOD_SECONDS;


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

    /**
     * Updates the scheduler period based on the ChangeStatus.
     * - If ChangeStatus is SINGLE_DOWN, DOUBLE_DOWN, SINGLE_UP, DOUBLE_UP, NONE, or UNDEFINED,
     * the period is set to 60s.
     * - If ChangeStatus is FLAT, FORTY_FIVE_UP, or FORTY_FIVE_DOWN, the period is set to 600s.
     *
     * @param changeStatus The ChangeStatus to determine the period
     */
    private void updateSchedulerPeriod(ChangeStatus changeStatus) {
        if (changeStatus == null) {
            return;
        }

        long newPeriodSeconds = ACCELERATED_STATUSES.contains(changeStatus)
                ? ACCELERATED_PERIOD_SECONDS
                : DEFAULT_PERIOD_SECONDS;

        if (newPeriodSeconds != currentPeriodSeconds) {
            Log.infof("Changing scheduler period from %ds to %ds due to ChangeStatus: %s",
                    currentPeriodSeconds, newPeriodSeconds, changeStatus);
            currentPeriodSeconds = newPeriodSeconds;
        }
    }

    @Scheduled(every = "60s")
    public void updateReadings() {
        Instant now = Instant.now();
        long secondsSinceLastExecution = now.getEpochSecond() - lastExecutionTime.getEpochSecond();

        // Only execute if enough time has passed based on the current period
        if (secondsSinceLastExecution < currentPeriodSeconds) {
            Log.debugf("Skipping execution, %d seconds passed out of %d seconds period",
                    secondsSinceLastExecution, currentPeriodSeconds);
            return;
        }

        lastExecutionTime = now;
        Log.infof("Executing scheduled task with period: %ds", currentPeriodSeconds);

        for (InfluxDbServerApi influxDbServerApi : influxDbServerApiClient.getJugglucoWebServerApiList()) {
            Optional<ICanReading> jugglucoStreamReadingOpt = tryToGetLastJugglucoStreamReading(influxDbServerApi);
            if (jugglucoStreamReadingOpt.isPresent()) {
                var jugglucoStreamReading = jugglucoStreamReadingOpt.get();
                sendUpdate(jugglucoStreamReading.toFormattedString());

                // Update the scheduler period based on the ChangeStatus
                updateSchedulerPeriod(jugglucoStreamReading.getChangeStatus());
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
