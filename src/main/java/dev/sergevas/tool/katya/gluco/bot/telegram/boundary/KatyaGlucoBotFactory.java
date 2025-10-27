package dev.sergevas.tool.katya.gluco.bot.telegram.boundary;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotProperties;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateDispatchProcessor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class KatyaGlucoBotFactory implements FactoryBean<KatyaGlucoBot> {

    private final TelegramBotProperties telegramBotProperties;
    private BotUpdateDispatchProcessor botCommandDispatchProcessor;

    public KatyaGlucoBotFactory(TelegramBotProperties telegramBotProperties) {
        this.telegramBotProperties = telegramBotProperties;
    }

    public BotUpdateDispatchProcessor getBotCommandDispatchProcessor() {
        return botCommandDispatchProcessor;
    }

    @Autowired
    public void setBotCommandDispatchProcessor(@Lazy BotUpdateDispatchProcessor botCommandDispatchProcessor) {
        this.botCommandDispatchProcessor = botCommandDispatchProcessor;
    }

    @Override
    public KatyaGlucoBot getObject() {
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

    @Override
    public Class<?> getObjectType() {
        return KatyaGlucoBot.class;
    }
}
