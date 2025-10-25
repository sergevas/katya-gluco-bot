package dev.sergevas.tool.katya.gluco.bot.telegram.boundary;

import dev.sergevas.tool.katya.gluco.bot.telegram.entity.ConversationContext;
import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

public class InMemoryConversationContextStore implements ConversationContextStore {

    private Map<Long, List<ConversationContext>> store;

    @PostConstruct
    public void init() {
        store = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<ConversationContext> getLast(Long chatId) {
        return Optional.ofNullable(store.get(chatId))
                .stream()
                .flatMap(Collection::stream)
                .filter(context -> !context.isDeleted())
                .max(Comparator.comparing(ConversationContext::getCreated));
    }

    @Override
    public ConversationContext put(Long chatId, ConversationContext context) {
        var storedContexts = store.get(chatId);
        if (isNull(storedContexts)) {
            storedContexts = new ArrayList<>();
            store.put(chatId, storedContexts);
        }
        storedContexts.add(context);
        return context;
    }

    @Override
    public Optional<ConversationContext> removeLast(Long chatId) {
        var lastContext = this.getLast(chatId);
        lastContext.ifPresent(context -> context.setDeleted(true));
        return lastContext;
    }

    @Scheduled(every = "24h")
    public void cleanup() {
        store.values().forEach(contexts -> contexts.removeIf(ConversationContext::isDeleted));
    }
}
