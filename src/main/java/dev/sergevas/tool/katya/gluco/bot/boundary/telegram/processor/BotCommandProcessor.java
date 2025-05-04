package dev.sergevas.tool.katya.gluco.bot.boundary.telegram.processor;


import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommandProcessor {

    void process(Update update);
}
