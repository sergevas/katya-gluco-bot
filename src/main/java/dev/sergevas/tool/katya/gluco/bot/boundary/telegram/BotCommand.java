package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import java.util.Optional;

public enum BotCommand {

    UPDATE("/update"),
    UNDEFINED(null);

    private final String command;

    BotCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static BotCommand findByCommand(String command) {
        return Optional.ofNullable(command).map(BotCommand::valueOf).orElse(UNDEFINED);
    }
}
