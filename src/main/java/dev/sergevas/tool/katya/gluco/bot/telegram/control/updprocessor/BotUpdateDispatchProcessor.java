package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotException;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.BotCommand;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

@ApplicationScoped
public class BotUpdateDispatchProcessor implements BotUpdateProcessor {

    private final ConversationContextStore conversationContextStore;
    private final BotUpdateValidationRules botUpdateValidationRules;
    private final BotUpdateProcessor botUpdateCommandProcessor;
    private final BotUpdateProcessor botInsCommandProcessor;
    private final BotUpdateProcessor botUnknownCommandProcessor;
    private final BotUpdateProcessor botErrorProcessor;
    private final BotUpdateProcessor botRecommendationRequestMessageProcessor;

    public BotUpdateDispatchProcessor(ConversationContextStore conversationContextStore,
                                      BotUpdateValidationRules botUpdateValidationRules,
                                      @Named("update") BotUpdateProcessor botUpdateCommandProcessor,
                                      @Named("ins") BotUpdateProcessor botInsCommandProcessor,
                                      @Named("unknown") BotUpdateProcessor botUnknownCommandProcessor,
                                      @Named("error") BotUpdateProcessor botErrorProcessor,
                                      @Named("recommendationRequest") BotUpdateProcessor botRecommendationRequestMessageProcessor) {
        this.conversationContextStore = conversationContextStore;
        this.botUpdateValidationRules = botUpdateValidationRules;
        this.botUpdateCommandProcessor = botUpdateCommandProcessor;
        this.botInsCommandProcessor = botInsCommandProcessor;
        this.botUnknownCommandProcessor = botUnknownCommandProcessor;
        this.botErrorProcessor = botErrorProcessor;
        this.botRecommendationRequestMessageProcessor = botRecommendationRequestMessageProcessor;
    }

    @Override
    public void process(Update update) {
        Log.infof("Enter process for %s", update);
        try {
            var message = update.getMessage();
            var user = message.getFrom();
            var chatId = user.getId();
            if (!botUpdateValidationRules.isUserValid(chatId)) {
                Log.warnf("User %s is not valid", chatId);
                return;
            }
            var isCommandPending = botUpdateValidationRules.isCommandPending(chatId);
            Log.infof("""
                            \n
                            **************************************************
                                      Have got preprocessing result
                            **************************************************
                            ***** Message ************************************
                            %s
                            **************************************************
                            user=%s
                            **************************************************
                            chatId=%s
                            **************************************************
                            isCommandPending=%s
                            **************************************************
                            
                            """,
                    message, user, chatId, isCommandPending);
            if (message.isCommand()) {
                // Here we discard all the pending commands and try to process the new one
                if (isCommandPending) {
                    conversationContextStore.removeLast(chatId);
                }
                var command = BotCommand.findByCommand(update.getMessage().getText());
                Log.debugf("Found command: %s", command);
                findBotCommandProcessor(command).process(update);
            } else {
                var context = conversationContextStore.getLast(chatId);
                if (isCommandPending
                        && BotCommand.isInsCommand(context.orElseThrow(() ->
                                new KatyaGlucoBotException("Unexpected state: the last ConversationContext instance should be present"))
                        .getCommandName())) {
                    botRecommendationRequestMessageProcessor.process(update);
                }
            }
        } catch (Exception e) {
            Log.error("Unable to process the update", e);
            botErrorProcessor.process(update);
        }
    }

    private BotUpdateProcessor findBotCommandProcessor(BotCommand botCommand) {
        return switch (botCommand) {
            case UPDATE -> botUpdateCommandProcessor;
            case INS -> botInsCommandProcessor;
            default -> botUnknownCommandProcessor;
        };
    }
}
