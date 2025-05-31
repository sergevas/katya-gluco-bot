package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.recommendation.boundary.RecommendationMessagesConfig;
import dev.sergevas.tool.katya.gluco.bot.recommendation.control.RecommendationProvider;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor.BotUpdateProcessor.chatId;

@ApplicationScoped
@Named("recommendationRequest")
public class BotRecommendationRequestMessageProcessor implements BotUpdateProcessor {

    private final KatyaGlucoBot katyaGlucoBot;
    private final RecommendationProvider recommendationProvider;
    private final RecommendationMessagesConfig recommendationMessagesConfig;
    private final ConversationContextStore conversationContextStore;

    public BotRecommendationRequestMessageProcessor(KatyaGlucoBot katyaGlucoBot,
                                                    RecommendationProvider recommendationProvider,
                                                    RecommendationMessagesConfig recommendationMessagesConfig,
                                                    ConversationContextStore conversationContextStore) {
        this.katyaGlucoBot = katyaGlucoBot;
        this.recommendationProvider = recommendationProvider;
        this.recommendationMessagesConfig = recommendationMessagesConfig;
        this.conversationContextStore = conversationContextStore;
    }

    @Override
    public void process(Update update) {
        Log.debug("Enter process");
        var chatId = chatId(update);
        var requestMessage = update.getMessage().getText();
        String text;
        if (isRequestMessageValid(requestMessage)) {
            text = recommendationProvider.provide(requestMessage).fullMessage();
        } else {
            text = recommendationMessagesConfig.messages().get("invalid-request-message");
        }
        katyaGlucoBot.sendMessageToChat(chatId.strId(), text);
        conversationContextStore.removeLast(chatId.id());
        Log.debug("Exit process");
    }

    private boolean isRequestMessageValid(String requestMessage) {
        return requestMessage != null && !requestMessage.isBlank();
    }
}
