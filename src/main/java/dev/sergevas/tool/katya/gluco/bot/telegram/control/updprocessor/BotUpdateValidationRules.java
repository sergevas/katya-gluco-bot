package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotConfig;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BotUpdateValidationRules {

    private final TelegramBotConfig telegramBotConfig;
    private final ConversationContextStore conversationContextStore;

    public BotUpdateValidationRules(TelegramBotConfig telegramBotConfig,
                                    ConversationContextStore conversationContextStore) {
        this.telegramBotConfig = telegramBotConfig;
        this.conversationContextStore = conversationContextStore;
    }

    public boolean isCommandPending(final Long chatId) {
        return conversationContextStore.getLast(chatId)
                .filter(context -> !context.isDeleted()
                        && context.isPending()).isPresent();
    }

    public boolean isUserValid(final Long userId) {
        return telegramBotConfig.chatIds().contains(String.valueOf(userId));
    }
}
