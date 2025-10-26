package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationMessagesProperties;
import dev.sergevas.tool.katya.gluco.bot.recommendation.control.RecommendationProvider;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateProcessor.chatId;

public class BotRecommendationRequestMessageProcessor implements BotUpdateProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(BotRecommendationRequestMessageProcessor.class)

    private final KatyaGlucoBot katyaGlucoBot;
    private final RecommendationProvider recommendationProvider;
    private final RecommendationMessagesProperties recommendationMessagesProperties;
    private final ConversationContextStore conversationContextStore;

    public BotRecommendationRequestMessageProcessor(KatyaGlucoBot katyaGlucoBot,
                                                    RecommendationProvider recommendationProvider,
                                                    RecommendationMessagesProperties recommendationMessagesProperties,
                                                    ConversationContextStore conversationContextStore) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.recommendationProvider = recommendationProvider;
        this.recommendationMessagesProperties = recommendationMessagesProperties;
        this.conversationContextStore = conversationContextStore;
    }

    @Override
    public void process(Update update) {
        LOG.debug("Enter process");
        var chatId = chatId(update);
        var requestMessage = update.getMessage().getText();
        String text;
        if (isRequestMessageValid(requestMessage)) {
            text = recommendationProvider.provide(requestMessage).fullMessage();
        } else {
            text = recommendationMessagesProperties.messages().get("invalid-request-message");
        }
        katyaGlucoBot.sendMessageToChat(chatId.strId(), text);
        conversationContextStore.removeLast(chatId.id());
        LOG.debug("Exit process");
    }

    private boolean isRequestMessageValid(String requestMessage) {
        return requestMessage != null && !requestMessage.isBlank();
    }
}
