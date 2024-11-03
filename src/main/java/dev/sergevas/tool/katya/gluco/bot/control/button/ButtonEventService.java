package dev.sergevas.tool.katya.gluco.bot.control.button;

import dev.sergevas.tool.katya.gluco.bot.boundary.telegram.TelegramBotUpdateSender;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ButtonEventService implements ButtonEventUseCase {

    @Inject
    TelegramBotUpdateSender telegramBotUpdateSender;

    @Override
    public void processEvent0001() {
        var message = "\uD83D\uDCA9";
        Log.info(message);
        telegramBotUpdateSender.sendUpdate(message);
    }
}
