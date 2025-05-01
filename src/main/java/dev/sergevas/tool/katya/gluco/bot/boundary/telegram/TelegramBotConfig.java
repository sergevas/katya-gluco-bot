package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import io.smallrye.config.ConfigMapping;

import java.util.List;

@ConfigMapping(prefix = "telegram")
public interface TelegramBotConfig {

    String botUsername();

    String token();

    List<String> chatIds();
}
