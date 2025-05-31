package dev.sergevas.tool.katya.gluco.bot.telegram.entity;

import java.util.Arrays;

public enum BotCommand {

    UPDATE("/update"),
    INS("/ins"),
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

    public static boolean isInsCommand(String command) {
        return INS.equals(findByCommand(command));
    }
}
