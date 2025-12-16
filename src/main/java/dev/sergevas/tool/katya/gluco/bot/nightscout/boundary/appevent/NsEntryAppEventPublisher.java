package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryNotifier;
import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NsEntryAppEventPublisher implements NsEntryNotifier {

    private static final Logger LOG = LoggerFactory.getLogger(NsEntryAppEventPublisher.class);

    @Override
    public void notify(NsEntry nsEntry) {
        LOG.debug("Enter notify() nsEntry = {}", nsEntry);

        LOG.debug("Exit notify()");
    }
}
