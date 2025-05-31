package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BotUpdateValidationRules {

    private final ConversationContextStore conversationContextStore;

    public BotUpdateValidationRules(ConversationContextStore conversationContextStore) {
        this.conversationContextStore = conversationContextStore;
    }

    public boolean isCommandPending(final String chatId, final String command) {
        return conversationContextStore.getLast(chatId)
                .filter(context -> !context.isDeleted()
                        && command.equals(context.getCommandName())
                        && context.isPending()).isPresent();
    }
}
