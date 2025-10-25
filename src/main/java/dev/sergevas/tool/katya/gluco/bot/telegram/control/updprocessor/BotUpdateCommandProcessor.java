package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotProperties;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.TextMessageFormatter;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.TriggerEvent;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.XDripReadingContext;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.ReadingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotUpdateCommandProcessor implements BotUpdateProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(BotUpdateCommandProcessor.class);

    private final KatyaGlucoBot katyaGlucoBot;
    private final ReadingService readingService;
    private final TelegramBotProperties telegramBotProperties;

    public BotUpdateCommandProcessor(KatyaGlucoBot katyaGlucoBot, ReadingService readingService,
                                     TelegramBotProperties telegramBotProperties) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.readingService = readingService;
        this.telegramBotProperties = telegramBotProperties;
    }

    @Override
    public void process(Update update) {
        LOG.debug("Enter process");
        var text = readingService.updateAndReturnLastReading()
                .map(reading -> new XDripReadingContext(reading, TriggerEvent.UPDATE))
                .map(TextMessageFormatter::formatUpdate)
                .orElse(telegramBotProperties.messages().get("no-data"));
        katyaGlucoBot.sendSensorReadingUpdateToAll(text);
        LOG.debug("Exit process");

    }
}
