package dev.sergevas.tool.katya.gluco.bot.telegram.boundary;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotProperties;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateDispatchProcessor;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class KatyaGlucoBotProvider {

    private final TelegramBotProperties telegramBotProperties;
    private final BotUpdateDispatchProcessor botCommandDispatchProcessor;

    public KatyaGlucoBotProvider(TelegramBotProperties telegramBotProperties,
                                 BotUpdateDispatchProcessor botCommandDispatchProcessor) {
        this.telegramBotProperties = telegramBotProperties;
        this.botCommandDispatchProcessor = botCommandDispatchProcessor;
    }

    @Produces
    @Singleton
    public KatyaGlucoBot katyaGlucoBot() {
        try {
            var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            var bot = new KatyaGlucoBot(telegramBotProperties.token(),
                    telegramBotProperties.botUsername(),
                    telegramBotProperties.chatIds(),
                    botCommandDispatchProcessor);
            telegramBotsApi.registerBot(bot);
            return bot;
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
