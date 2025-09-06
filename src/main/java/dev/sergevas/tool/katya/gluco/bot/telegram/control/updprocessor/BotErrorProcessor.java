package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotMessages;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotApp.LOG;

@ApplicationScoped
@Named("error")
public class BotErrorProcessor implements BotUpdateProcessor {

    private final KatyaGlucoBot katyaGlucoBot;
    private final TelegramBotMessages telegramBotMessages;
    private final ConversationContextStore conversationContextStore;

    @Inject
    public BotErrorProcessor(
            KatyaGlucoBot katyaGlucoBot,
            TelegramBotMessages telegramBotMessages,
            ConversationContextStore conversationContextStore) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.telegramBotMessages = telegramBotMessages;
        this.conversationContextStore = conversationContextStore;
    }

    @Override
    public void process(Update update) {
        LOG.fine("Enter process");
        var message = update.getMessage();
        var messageText = message.getText();
        var messageUnableToProcess = String.format("%s:\n'%s'", telegramBotMessages.unableToProcess, messageText);
        var chatId = message.getFrom().getId();
        katyaGlucoBot.sendMessageToChat(String.valueOf(chatId), messageUnableToProcess);
        conversationContextStore.removeLast(chatId);
        LOG.fine("Exit process");
    }
}
