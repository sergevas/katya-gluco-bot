package dev.sergevas.tool.katya.gluco.bot.adapter.in.filewatcher;

import dev.sergevas.tool.katya.gluco.bot.application.port.in.filewatcher.ResourceUpdateUseCase;
import io.methvin.watcher.DirectoryWatcher;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DirectoryWatchingAdapter {

    @Inject
    ResourceUpdateUseCase resourceUpdateUseCase;

    @Inject
    DirectoryWatcher directoryWatcher;

    @Startup
    void start() {
        Log.info("Start DirectoryWatchingAdapter");
        directoryWatcher.watchAsync();
    }
}
