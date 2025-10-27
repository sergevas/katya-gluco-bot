package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotException;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.BotCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotUpdateDispatchProcessor implements BotUpdateProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(BotUpdateDispatchProcessor.class);

    private final ConversationContextStore conversationContextStore;
    private final BotUpdateValidationRules botUpdateValidationRules;
    private final BotUpdateProcessor botUpdateCommandProcessor;
    private final BotUpdateProcessor botInsCommandProcessor;
    private final BotUpdateProcessor botUnknownCommandProcessor;
    private final BotUpdateProcessor botErrorProcessor;
    private final BotUpdateProcessor botRecommendationRequestMessageProcessor;

    public BotUpdateDispatchProcessor(ConversationContextStore conversationContextStore,
                                      BotUpdateValidationRules botUpdateValidationRules,
                                      BotUpdateProcessor botUpdateCommandProcessor,
                                      BotUpdateProcessor botInsCommandProcessor,
                                      BotUpdateProcessor botUnknownCommandProcessor,
                                      BotUpdateProcessor botErrorProcessor,
                                      BotUpdateProcessor botRecommendationRequestMessageProcessor) {
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
        LOG.info("Enter process for {}", update);
        try {
            var message = update.getMessage();
            var user = message.getFrom();
            var chatId = user.getId();
            if (!botUpdateValidationRules.isUserValid(chatId)) {
                LOG.warn("User {} is not valid", chatId);
                return;
            }
            var isCommandPending = botUpdateValidationRules.isCommandPending(chatId);
            LOG.info("""
                            \n
                            **************************************************
                                      Have got preprocessing result
                            **************************************************
                            ***** Message ************************************
                            {}
                            **************************************************
                            user={}
                            **************************************************
                            chatId={}
                            **************************************************
                            isCommandPending={}
                            **************************************************
                            
                            """,
                    message, user, chatId, isCommandPending);
            if (message.isCommand()) {
                // Here we discard all the pending commands and try to process the new one
                if (isCommandPending) {
                    conversationContextStore.removeLast(chatId);
                }
                var command = BotCommand.findByCommand(update.getMessage().getText());
                LOG.debug("Found command: {}", command);
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
            LOG.error("Unable to process the update", e);
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
