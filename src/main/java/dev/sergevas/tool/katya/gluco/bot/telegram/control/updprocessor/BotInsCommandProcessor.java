package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationMessagesProperties;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.BotCommand;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.ConversationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.OffsetDateTime;

import static dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateProcessor.chatId;

public class BotInsCommandProcessor implements BotUpdateProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(BotInsCommandProcessor.class);

    private final KatyaGlucoBot katyaGlucoBot;
    private final RecommendationMessagesProperties recommendationMessagesProperties;
    private final ConversationContextStore conversationContextStore;

    public BotInsCommandProcessor(
            KatyaGlucoBot katyaGlucoBot,
            RecommendationMessagesProperties recommendationMessagesProperties,
            ConversationContextStore conversationContextStore) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.recommendationMessagesProperties = recommendationMessagesProperties;
        this.conversationContextStore = conversationContextStore;
    }

    @Override
    public void process(Update update) {
        LOG.debug("Enter process");
        var chatId = chatId(update);
        var text = recommendationMessagesProperties.messages().get("prompt");
        conversationContextStore.put(chatId.id(), new ConversationContext(
                chatId.id(),
                update.getMessage().getMessageId(),
                BotCommand.INS.getCommand(),
                Boolean.TRUE,
                OffsetDateTime.now(),
                Boolean.FALSE));
        katyaGlucoBot.sendMessageToChat(chatId.strId(), text);
        LOG.debug("Exit process");
    }
}
