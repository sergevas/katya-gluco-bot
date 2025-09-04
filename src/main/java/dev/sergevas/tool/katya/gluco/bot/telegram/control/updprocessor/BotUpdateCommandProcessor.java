package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotMessages;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.TextMessageFormatter;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.TriggerEvent;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.XDripReadingContext;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.ReadingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotApp.LOG;

@ApplicationScoped
@Named("update")
public class BotUpdateCommandProcessor implements BotUpdateProcessor {

    private final KatyaGlucoBot katyaGlucoBot;
    private final ReadingService readingService;
    private final TelegramBotMessages telegramBotMessages;

    @Inject
    public BotUpdateCommandProcessor(KatyaGlucoBot katyaGlucoBot, ReadingService readingService,
                                     TelegramBotMessages telegramBotMessages) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.readingService = readingService;
        this.telegramBotMessages = telegramBotMessages;
    }

    @Override
    public void process(Update update) {
        LOG.fine("Enter process");
        var text = readingService.updateAndReturnLastReading()
                .map(reading -> new XDripReadingContext(reading, TriggerEvent.UPDATE))
                .map(TextMessageFormatter::formatUpdate)
                .orElse(telegramBotMessages.noData);
        katyaGlucoBot.sendSensorReadingUpdateToAll(text);
        LOG.fine("Exit process");

    }
}
