package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

public enum BotCommand {

    UPDATE("/update");

    private final String command;

    BotCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
