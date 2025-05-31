package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;


import dev.sergevas.tool.katya.gluco.bot.telegram.entity.ChatId;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotUpdateProcessor {

    void process(Update update);

    static ChatId chatId(Update update) {
        return new ChatId(update.getMessage().getChatId());
    }
}
