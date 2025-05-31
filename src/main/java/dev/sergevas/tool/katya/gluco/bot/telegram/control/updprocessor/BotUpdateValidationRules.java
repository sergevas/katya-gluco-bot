package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BotUpdateValidationRules {

    private final ConversationContextStore conversationContextStore;

    public BotUpdateValidationRules(ConversationContextStore conversationContextStore) {
        this.conversationContextStore = conversationContextStore;
    }

    public boolean isCommandPending(final Long chatId) {
        return conversationContextStore.getLast(chatId)
                .filter(context -> !context.isDeleted()
                        && context.isPending()).isPresent();
    }
}
