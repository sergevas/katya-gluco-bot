package dev.sergevas.tool.katya.gluco.bot.telegram;

import org.eclipse.microprofile.config.inject.ConfigProperties;

import java.util.List;

@ConfigProperties(prefix = "telegram")
public class TelegramBotConfig {

    public String botUsername;
    public String token;
    public List<String> chatIds;
}
