package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryPublisher;
import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

public class NsEntryAppEventPublisher implements NsEntryPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(NsEntryAppEventPublisher.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public NsEntryAppEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(NsEntry nsEntry) {
        LOG.debug("Enter notify() nsEntry = {}", nsEntry);
        applicationEventPublisher.publishEvent(nsEntry);
        LOG.debug("Exit notify()");
    }
}
