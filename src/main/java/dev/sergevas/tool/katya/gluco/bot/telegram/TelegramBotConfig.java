package dev.sergevas.tool.katya.gluco.bot.telegram;

import io.smallrye.config.ConfigMapping;

import java.util.List;
import java.util.Map;

@ConfigMapping(prefix = "telegram")
public interface TelegramBotConfig {

    String botUsername();

    String token();

    List<String> chatIds();

    Map<String, String> messages();
}
