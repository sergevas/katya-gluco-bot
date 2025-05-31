package dev.sergevas.tool.katya.gluco.bot.telegram.boundary;

import dev.sergevas.tool.katya.gluco.bot.telegram.entity.ConversationContext;

import java.util.Optional;

public interface ConversationContextStore {

    Optional<ConversationContext> getLast(Long chatId);

    ConversationContext put(Long chatId, ConversationContext context);

    Optional<ConversationContext> removeLast(Long chatId);
}
