package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryPublishedUseCase;
import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NsEntryPublishedLoggingHandler implements NsEntryPublishedUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(NsEntryPublishedLoggingHandler.class);

    @Override
    public void handle(NsEntry nsEntry) {
        LOG.info("Enter {}.handle() nsEntry = {}", NsEntryPublishedLoggingHandler.class, nsEntry);
        LOG.info("Exit {}.handle()", NsEntryPublishedLoggingHandler.class);
    }
}
