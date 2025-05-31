package dev.sergevas.tool.katya.gluco.bot.telegram.boundary;

import dev.sergevas.tool.katya.gluco.bot.telegram.entity.ConversationContext;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

@ApplicationScoped
public class InMemoryConversationContextStore implements ConversationContextStore {

    private Map<String, List<ConversationContext>> store;

    @PostConstruct
    public void init() {
        store = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<ConversationContext> getLast(String chatId) {
        return Optional.ofNullable(store.get(chatId))
                .stream()
                .flatMap(Collection::stream)
                .filter(context -> !context.isDeleted())
                .max(Comparator.comparing(ConversationContext::getCreated));
    }

    @Override
    public ConversationContext put(String chatId, ConversationContext context) {
        var storedContexts = store.get(chatId);
        if (isNull(storedContexts)) {
            storedContexts = new ArrayList<>();
            store.put(chatId, storedContexts);
        }
        storedContexts.add(context);
        return context;
    }

    @Override
    public Optional<ConversationContext> removeLast(String chatId) {
        var lastContext = this.getLast(chatId);
        lastContext.ifPresent(context -> context.setDeleted(true));
        return lastContext;
    }
}
