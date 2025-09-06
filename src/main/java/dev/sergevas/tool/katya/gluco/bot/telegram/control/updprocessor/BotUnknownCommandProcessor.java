package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotApp.LOG;

@ApplicationScoped
@Named("unknown")
public class BotUnknownCommandProcessor implements BotUpdateProcessor {

    @Override
    public void process(Update update) {
        LOG.fine("Enter unknown command. Do nothing");
        //Do nothing
        LOG.fine("Exit unknown command");
    }
}
