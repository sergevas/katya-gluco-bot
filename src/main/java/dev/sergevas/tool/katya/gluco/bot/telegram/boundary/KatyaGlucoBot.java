package dev.sergevas.tool.katya.gluco.bot.telegram.boundary;

import dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateDispatchProcessor;
import io.quarkus.logging.Log;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class KatyaGlucoBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final List<String> chatIds;
    private final BotUpdateDispatchProcessor botCommandDispatchProcessor;

    public KatyaGlucoBot(String token, String botUsername, List<String> chatIds,
                         BotUpdateDispatchProcessor botCommandDispatchProcessor) {
        super(token);
        this.botUsername = botUsername;
        this.chatIds = chatIds;
        this.botCommandDispatchProcessor = botCommandDispatchProcessor;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Log.debugf("Enter onUpdateReceived for %s", update);
        if (update.hasMessage()) {
            processMessage(update);
        }
        Log.debug("Exit onUpdateReceived");
    }

    private void processMessage(Update update) {
        this.botCommandDispatchProcessor.process(update);
    }

    public void sendSensorReadingUpdateToAll(final String text) {
        chatIds.forEach(chatId -> {
            sendMessageToChat(chatId, text);
        });
    }

    public void sendMessageToChat(final String chatId, final String text) {
        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .text(text)
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            Log.error(e);
        }

    }
}
