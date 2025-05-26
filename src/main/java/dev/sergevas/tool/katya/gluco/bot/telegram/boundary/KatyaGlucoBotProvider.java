package dev.sergevas.tool.katya.gluco.bot.telegram.boundary;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotConfig;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.BotCommandDispatchProcessor;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class KatyaGlucoBotProvider {

    private final TelegramBotConfig telegramBotConfig;
    private final BotCommandDispatchProcessor botCommandDispatchProcessor;

    public KatyaGlucoBotProvider(TelegramBotConfig telegramBotConfig,
                                 BotCommandDispatchProcessor botCommandDispatchProcessor) {
        this.telegramBotConfig = telegramBotConfig;
        this.botCommandDispatchProcessor = botCommandDispatchProcessor;
    }

    @Produces
    @Singleton
    public KatyaGlucoBot katyaGlucoBot() {
        try {
            var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            var bot = new KatyaGlucoBot(telegramBotConfig.token(),
                    telegramBotConfig.botUsername(),
                    telegramBotConfig.chatIds(),
                    botCommandDispatchProcessor);
            telegramBotsApi.registerBot(bot);
            return bot;
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
