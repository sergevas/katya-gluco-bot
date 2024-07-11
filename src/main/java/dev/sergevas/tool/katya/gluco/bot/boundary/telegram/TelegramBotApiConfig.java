package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import io.smallrye.config.ConfigMapping;

import java.util.List;

@ConfigMapping(prefix = "telegram")
public interface TelegramBotApiConfig {

    String token();

    List<String> chatIds();
}
