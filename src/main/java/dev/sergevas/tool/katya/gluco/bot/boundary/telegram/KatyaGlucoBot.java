package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import io.quarkus.logging.Log;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class KatyaGlucoBot extends TelegramLongPollingBot {

    private String botUsername;
    private List<String> chatIds;

    public KatyaGlucoBot(String token, String botUsername, List<String> chatIds) {
        super(token);
        this.botUsername = botUsername;
        this.chatIds = chatIds;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Log.debugf("Enter onUpdateReceived for %s", update);
        Log.debug("Exit onUpdateReceived");
    }

    public void sendSensorReadingUpdate(final String text) {
        chatIds.forEach(chatId -> {
            var sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .build();
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                Log.error(e);
            }
        });
    }
}
