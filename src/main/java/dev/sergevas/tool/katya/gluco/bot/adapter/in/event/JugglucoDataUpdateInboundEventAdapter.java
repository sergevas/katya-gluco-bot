package dev.sergevas.tool.katya.gluco.bot.adapter.in.event;

import dev.sergevas.tool.katya.gluco.bot.application.port.in.filewatcher.ResourceUpdateHandler;
import io.methvin.watcher.DirectoryChangeEvent;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

@ApplicationScoped
public class JugglucoDataUpdateInboundEventAdapter {

    @Inject
    ResourceUpdateHandler resourceUpdateHandler;

    public void onDirectoryChangeEvent(@ObservesAsync final DirectoryChangeEvent directoryChangeEvent) {
        Log.debugf("onDirectoryChangeEvent: %s", directoryChangeEvent);
    }
}
