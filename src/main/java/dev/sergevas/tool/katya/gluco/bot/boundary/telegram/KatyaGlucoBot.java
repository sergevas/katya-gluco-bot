package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class KatyaGlucoBot extends TelegramLongPollingBot {

    private String botUsername;

    public KatyaGlucoBot(String token) {
        super(token);
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
