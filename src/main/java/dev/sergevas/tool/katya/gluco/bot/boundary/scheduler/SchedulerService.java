package dev.sergevas.tool.katya.gluco.bot.boundary.scheduler;

import dev.sergevas.tool.katya.gluco.bot.boundary.juggluco.JugglucoWebServerApiClient;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.TelegramBotUpdateSender;
import dev.sergevas.tool.katya.gluco.bot.control.LastReadingCacheManager;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SchedulerService {

    private final JugglucoWebServerApiClient jugglucoWebServerApiClient;
    private final LastReadingCacheManager lastReadingCacheManager;
    @Inject
    TelegramBotUpdateSender telegramBotUpdateSender;


    public SchedulerService(
            JugglucoWebServerApiClient jugglucoWebServerApiClient,
            LastReadingCacheManager lastReadingCacheManager
    ) {
        this.jugglucoWebServerApiClient = jugglucoWebServerApiClient;
        this.lastReadingCacheManager = lastReadingCacheManager;
    }

    @Scheduled(every = "600s")
    public void updateReadings() {
    }


}
