package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotException;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.BotCommand;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotApp.LOG;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

@ApplicationScoped
public class BotUpdateDispatchProcessor implements BotUpdateProcessor {

    private final ConversationContextStore conversationContextStore;
    private final BotUpdateValidationRules botUpdateValidationRules;
    private final BotUpdateProcessor botUpdateCommandProcessor;
    private final BotUpdateProcessor botInsCommandProcessor;
    private final BotUpdateProcessor botUnknownCommandProcessor;
    private final BotUpdateProcessor botErrorProcessor;
    private final BotUpdateProcessor botRecommendationRequestMessageProcessor;

    @Inject
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
        LOG.log(INFO, "Enter process for {0}", update);
        try {
            var message = update.getMessage();
            var user = message.getFrom();
            var chatId = user.getId();
            if (!botUpdateValidationRules.isUserValid(chatId)) {
                LOG.log(WARNING, "User {0} is not valid", chatId);
                return;
            }
            var isCommandPending = botUpdateValidationRules.isCommandPending(chatId);
            LOG.log(INFO, """
                    \n
                    **************************************************
                              Have got preprocessing result
                    **************************************************
                    ***** Message ************************************
                    {0}
                    **************************************************
                    user={1}
                    **************************************************
                    chatId={2}
                    **************************************************
                    isCommandPending={3}
                    **************************************************
                    
                    """, new Object[]{message, user, chatId, isCommandPending});
            if (message.isCommand()) {
                // Here we discard all the pending commands and try to process the new one
                if (isCommandPending) {
                    conversationContextStore.removeLast(chatId);
                }
                var command = BotCommand.findByCommand(update.getMessage().getText());
                LOG.log(FINE, "Found command: {0}", command);
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
            LOG.log(SEVERE, "Unable to process the update", e);
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
