package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotConfig;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

@ApplicationScoped
@Named("messageValidationError")
public class BotValidationErrorMessageProcessor implements BotUpdateProcessor {

    private final KatyaGlucoBot katyaGlucoBot;
    private final TelegramBotConfig telegramBotConfig;
    private final ConversationContextStore conversationContextStore;

    public BotValidationErrorMessageProcessor(
            KatyaGlucoBot katyaGlucoBot,
            TelegramBotConfig telegramBotConfig,
            ConversationContextStore conversationContextStore) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.telegramBotConfig = telegramBotConfig;
        this.conversationContextStore = conversationContextStore;
    }

    @Override
    public void process(Update update) {
        Log.debug("Enter process");
        var message = update.getMessage();
        var messageText = message.getText();
        var messageUnableToProcess = String.format("%s: '%s'", telegramBotConfig.messages().get("message-unable-to-process"), messageText);
        var chatId = String.valueOf(message.getFrom().getId());
        katyaGlucoBot.sendMessageToChat(chatId, messageUnableToProcess);
        conversationContextStore.removeLast(chatId);
        Log.debug("Exit process");
    }
}
