package dev.sergevas.tool.katya.gluco.bot.boundary.telegram.processor;

import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.TextMessageFormatter;
import dev.sergevas.tool.katya.gluco.bot.control.ReadingService;
import dev.sergevas.tool.katya.gluco.bot.entity.TriggerEvent;
import dev.sergevas.tool.katya.gluco.bot.entity.XDripReadingContext;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

@ApplicationScoped
@Named("update")
public class BotUpdateCommandProcessor implements BotCommandProcessor {

    private final KatyaGlucoBot katyaGlucoBot;
    private final ReadingService readingService;

    public BotUpdateCommandProcessor(KatyaGlucoBot katyaGlucoBot, ReadingService readingService) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.readingService = readingService;
    }

    @Override
    public void process(Update update) {
        Log.debug("Enter process");
        var text = readingService.updateAndReturnLastReading()
                .map(reading -> new XDripReadingContext(reading, TriggerEvent.UPDATE))
                .map(TextMessageFormatter::formatUpdate)
                .orElse("Нет данных");
        katyaGlucoBot.sendSensorReadingUpdate(text);
        Log.debug("Exit process");

    }
}
