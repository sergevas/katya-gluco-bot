package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.telegram.telegrambots.meta.generics.TelegramBot;

public class BotProvider {

    private final TelegramBotApiConfig telegramBotApiConfig;

    public BotProvider(TelegramBotApiConfig telegramBotApiConfig) {
        this.telegramBotApiConfig = telegramBotApiConfig;
    }

    @Produces
    @ApplicationScoped
    public TelegramBot telegramBot() {

    }
}
