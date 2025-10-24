package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotProperties;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.TextMessageFormatter;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.TriggerEvent;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.XDripReadingContext;
import dev.sergevas.tool.katya.gluco.bot.xdrip.control.ReadingService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

@ApplicationScoped
@Named("update")
public class BotUpdateCommandProcessor implements BotUpdateProcessor {

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
        Log.debug("Enter process");
        var text = readingService.updateAndReturnLastReading()
                .map(reading -> new XDripReadingContext(reading, TriggerEvent.UPDATE))
                .map(TextMessageFormatter::formatUpdate)
                .orElse(telegramBotProperties.messages().get("no-data"));
        katyaGlucoBot.sendSensorReadingUpdateToAll(text);
        Log.debug("Exit process");

    }
}
