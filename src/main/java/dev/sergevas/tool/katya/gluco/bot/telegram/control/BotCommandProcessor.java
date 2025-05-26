package dev.sergevas.tool.katya.gluco.bot.telegram.control;


import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommandProcessor {

    void process(Update update);
}
