package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotProperties;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BotUpdateValidationRules {

    private final TelegramBotProperties telegramBotProperties;
    private final ConversationContextStore conversationContextStore;

    public BotUpdateValidationRules(TelegramBotProperties telegramBotProperties,
                                    ConversationContextStore conversationContextStore) {
        this.telegramBotProperties = telegramBotProperties;
        this.conversationContextStore = conversationContextStore;
    }

    public boolean isCommandPending(final Long chatId) {
        return conversationContextStore.getLast(chatId)
                .filter(context -> !context.isDeleted()
                        && context.isPending()).isPresent();
    }

    public boolean isUserValid(final Long userId) {
        return telegramBotProperties.chatIds().contains(String.valueOf(userId));
    }
}
