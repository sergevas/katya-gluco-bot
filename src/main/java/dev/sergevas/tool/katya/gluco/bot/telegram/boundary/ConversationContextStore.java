package dev.sergevas.tool.katya.gluco.bot.telegram.boundary;

import dev.sergevas.tool.katya.gluco.bot.telegram.entity.ConversationContext;

import java.util.Optional;

public interface ConversationContextStore {

    Optional<ConversationContext> getLast(String chatId);

    ConversationContext put(String chatId, ConversationContext context);

    Optional<ConversationContext> removeLast(String chatId);
}
