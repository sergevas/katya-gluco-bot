package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryPublishedUseCase;
import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;
import org.springframework.context.event.EventListener;

public class NsEntryAppEventListener {

    private final NsEntryPublishedUseCase nsEntryPublishedUseCase;

    public NsEntryAppEventListener(NsEntryPublishedUseCase nsEntryPublishedUseCase) {
        this.nsEntryPublishedUseCase = nsEntryPublishedUseCase;
    }

    @EventListener
    public void onNsEntryPublished(NsEntry nsEntry) {
        this.nsEntryPublishedUseCase.handle(nsEntry);
    }
}
