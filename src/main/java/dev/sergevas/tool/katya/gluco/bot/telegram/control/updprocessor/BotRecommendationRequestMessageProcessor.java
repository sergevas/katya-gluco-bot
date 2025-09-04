package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotApp.LOG;

@ApplicationScoped
@Named("recommendationRequest")
public class BotRecommendationRequestMessageProcessor implements BotUpdateProcessor {

    private final KatyaGlucoBot katyaGlucoBot;
    //    private final RecommendationProvider recommendationProvider;
//    private final RecommendationMessagesConfig recommendationMessagesConfig;
    private final ConversationContextStore conversationContextStore;

    @Inject
    public BotRecommendationRequestMessageProcessor(KatyaGlucoBot katyaGlucoBot,
//                                                    RecommendationProvider recommendationProvider,
//                                                    RecommendationMessagesConfig recommendationMessagesConfig,
                                                    ConversationContextStore conversationContextStore) {
        this.katyaGlucoBot = katyaGlucoBot;
//        this.recommendationProvider = recommendationProvider;
//        this.recommendationMessagesConfig = recommendationMessagesConfig;
        this.conversationContextStore = conversationContextStore;
    }

    @Override
    public void process(Update update) {
        LOG.fine("Enter process");
//        var chatId = chatId(update);
//        var requestMessage = update.getMessage().getText();
//        String text;
//        if (isRequestMessageValid(requestMessage)) {
//            text = recommendationProvider.provide(requestMessage).fullMessage();
//        } else {
//            text = recommendationMessagesConfig.messages().get("invalid-request-message");
//        }
//        katyaGlucoBot.sendMessageToChat(chatId.strId(), text);
//        conversationContextStore.removeLast(chatId.id());
        LOG.fine("Exit process");
    }

    private boolean isRequestMessageValid(String requestMessage) {
        return requestMessage != null && !requestMessage.isBlank();
    }
}
