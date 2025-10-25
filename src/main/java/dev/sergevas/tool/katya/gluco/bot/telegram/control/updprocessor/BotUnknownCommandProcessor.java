package dev.sergevas.tool.katya.gluco.bot.telegram.control.updprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotUnknownCommandProcessor implements BotUpdateProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(BotUnknownCommandProcessor.class);

    @Override
    public void process(Update update) {
        LOG.debug("Enter unknown command. Do nothing");
        //Do nothing
        LOG.debug("Exit unknown command");
    }
}
