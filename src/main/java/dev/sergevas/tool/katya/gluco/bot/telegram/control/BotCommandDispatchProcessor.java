package dev.sergevas.tool.katya.gluco.bot.telegram.control;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@ApplicationScoped
public class BotCommandDispatchProcessor implements BotCommandProcessor {

    public enum BotCommand {

        UPDATE("/update"),
        UNITS("/units"),
        UNKNOWN(null);

        private final String command;

        BotCommand(String command) {
            this.command = command;
        }

        public String getCommand() {
            return command;
        }

        public static BotCommand findByCommand(String command) {
            return Arrays.stream(values())
                    .filter(botCommand -> botCommand.getCommand().equals(command))
                    .findAny()
                    .orElse(UNKNOWN);
        }
    }

    private final BotCommandProcessor botUpdateCommandProcessor;
    private final BotCommandProcessor botUnitsCommandProcessor;
    private final BotCommandProcessor botUnknownCommandProcessor;

    public BotCommandDispatchProcessor(@Named("update") BotCommandProcessor botUpdateCommandProcessor,
                                       @Named("units") BotCommandProcessor botUnitsCommandProcessor,
                                       @Named("unknown") BotCommandProcessor botUnknownCommandProcessor) {
        this.botUpdateCommandProcessor = botUpdateCommandProcessor;
        this.botUnitsCommandProcessor = botUnitsCommandProcessor;
        this.botUnknownCommandProcessor = botUnknownCommandProcessor;
    }

    @Override
    public void process(Update update) {
        Log.debugf("Enter process for %s", update);
        var command = BotCommand.findByCommand(update.getMessage().getText());
        Log.debugf("Found command: %s", command);
        findBotCommandProcessor(command).process(update);
    }

    private BotCommandProcessor findBotCommandProcessor(BotCommand botCommand) {
        return switch (botCommand) {
            case UPDATE -> botUpdateCommandProcessor;
            case UNITS -> botUnitsCommandProcessor;
            default -> botUnknownCommandProcessor;
        };
    }
}
