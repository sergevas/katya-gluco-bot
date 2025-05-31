package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.ConversationContextStore;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.BotCommand;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

@ApplicationScoped
public class BotUpdateDispatchProcessor implements BotUpdateProcessor {

    private final ConversationContextStore conversationContextStore;
    private final BotUpdateProcessor botUpdateCommandProcessor;
    private final BotUpdateProcessor botUnitsCommandProcessor;
    private final BotUpdateProcessor botUnknownCommandProcessor;
    private final BotUpdateProcessor botValidationErrorMessageProcessor;

    public BotUpdateDispatchProcessor(ConversationContextStore conversationContextStore,
                                      @Named("update") BotUpdateProcessor botUpdateCommandProcessor,
                                      @Named("units") BotUpdateProcessor botUnitsCommandProcessor,
                                      @Named("unknown") BotUpdateProcessor botUnknownCommandProcessor,
                                      @Named("messageValidationError") BotUpdateProcessor botValidationErrorMessageProcessor) {
        this.conversationContextStore = conversationContextStore;
        this.botUpdateCommandProcessor = botUpdateCommandProcessor;
        this.botUnitsCommandProcessor = botUnitsCommandProcessor;
        this.botUnknownCommandProcessor = botUnknownCommandProcessor;
        this.botValidationErrorMessageProcessor = botValidationErrorMessageProcessor;
    }

    @Override
    public void process(Update update) {
        Log.debugf("Enter process for %s", update);
        var message = update.getMessage();
        var user = message.getFrom();
        var id = user.getId();
        if (message.isCommand()) {
            var command = BotCommand.findByCommand(update.getMessage().getText());
            Log.debugf("Found command: %s", command);
            findBotCommandProcessor(command).process(update);
        } else {
            Log.debug("This is not a command");
        }
    }

    private BotUpdateProcessor findBotCommandProcessor(BotCommand botCommand) {
        return switch (botCommand) {
            case UPDATE -> botUpdateCommandProcessor;
            case UNITS -> botUnitsCommandProcessor;
            default -> botUnknownCommandProcessor;
        };
    }
}
