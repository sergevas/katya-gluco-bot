package dev.sergevas.tool.katya.gluco.bot.boundary.telegram.processor;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

@ApplicationScoped
@Named("unknown")
public class BotUnknownCommandProcessor implements BotCommandProcessor {

    @Override
    public void process(Update update) {
        Log.debug("Enter unknown command. Do nothing");
        //Do nothing
        Log.debug("Exit unknown command");
    }
}
