package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class TelegramBotUpdateSender {

    @Inject
    TelegramBotApiConfig telegramBotApiConfig;

    @Inject
    @RestClient
    KatyaGlucoBotApiClient katyaGlucoBotApiClient;

    public void sendUpdate(final String text) {
        try {
            telegramBotApiConfig.chatIds().forEach(chatId -> katyaGlucoBotApiClient.sendUpdate(telegramBotApiConfig.token(),
                    chatId, text));
        } catch (Exception e) {
            Log.error(e);
        }
    }
}
