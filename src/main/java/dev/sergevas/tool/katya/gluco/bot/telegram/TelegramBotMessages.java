package dev.sergevas.tool.katya.gluco.bot.telegram;

import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "telegram.messages")
public class TelegramBotMessages {

    public String noData;
    public String unableToProcess;
}
