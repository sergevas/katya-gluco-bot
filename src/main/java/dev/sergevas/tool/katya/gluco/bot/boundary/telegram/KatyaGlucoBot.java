package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.processor.BotCommandDispatchProcessor;
import io.quarkus.logging.Log;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class KatyaGlucoBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final List<String> chatIds;
    private final BotCommandDispatchProcessor botCommandDispatchProcessor;

    public KatyaGlucoBot(String token, String botUsername, List<String> chatIds,
                         BotCommandDispatchProcessor botCommandDispatchProcessor) {
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
        var message = update.getMessage();
        var user = message.getFrom();
        var id = user.getId();
        if (message.isCommand()) {
            this.botCommandDispatchProcessor.process(update);
        } else {
            Log.debug("This is not a command");
        }
    }

    public void sendSensorReadingUpdate(final String text) {
        chatIds.forEach(chatId -> {
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
        });
    }
}
