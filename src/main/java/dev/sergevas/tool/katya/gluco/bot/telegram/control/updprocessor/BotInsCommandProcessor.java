package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationMessagesConfig;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.BotCommand;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.ConversationContext;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.OffsetDateTime;

import static dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateProcessor.chatId;

@ApplicationScoped
@Named("ins")
public class BotInsCommandProcessor implements BotUpdateProcessor {

    private final KatyaGlucoBot katyaGlucoBot;
    private final RecommendationMessagesConfig recommendationMessagesConfig;
    private final ConversationContextStore conversationContextStore;

    public BotInsCommandProcessor(
            KatyaGlucoBot katyaGlucoBot,
            RecommendationMessagesConfig recommendationMessagesConfig,
            ConversationContextStore conversationContextStore) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.recommendationMessagesConfig = recommendationMessagesConfig;
        this.conversationContextStore = conversationContextStore;
    }

    @Override
    public void process(Update update) {
        Log.debug("Enter process");
        var chatId = chatId(update);
        var text = recommendationMessagesConfig.messages().get("prompt");
        conversationContextStore.put(chatId.id(), new ConversationContext(
                chatId.id(),
                update.getMessage().getMessageId(),
                BotCommand.INS.getCommand(),
                Boolean.TRUE,
                OffsetDateTime.now(),
                Boolean.FALSE));
        katyaGlucoBot.sendMessageToChat(chatId.strId(), text);
        Log.debug("Exit process");
    }
}
