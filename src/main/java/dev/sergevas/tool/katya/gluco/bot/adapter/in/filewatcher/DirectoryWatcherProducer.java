package dev.sergevas.tool.katya.gluco.bot.adapter.in.filewatcher;

import io.methvin.watcher.DirectoryChangeEvent;
import io.methvin.watcher.DirectoryWatcher;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.file.Path;

public class DirectoryWatcherProducer {

    private final String baseDir;
    private final Event<DirectoryChangeEvent> directoryChangeEventEvent;

    @Inject
    public DirectoryWatcherProducer(@ConfigProperty(name = "filewatcher.basedir") String baseDir,
                                    Event<DirectoryChangeEvent> directoryChangeEventEvent) {
        this.baseDir = baseDir;
        this.directoryChangeEventEvent = directoryChangeEventEvent;
    }

    @Produces
    @ApplicationScoped
    public DirectoryWatcher directoryWatcher() {
        Log.info("Produce DirectoryWatcher");
        try {
            return DirectoryWatcher.builder()
                    .path(Path.of(baseDir))
                    .listener(directoryChangeEventEvent::fireAsync)
                    .build();
        } catch (Exception e) {
            throw new DirectoryWatcherException("Unable to create directory watcher", e);
        }
    }

    public void disposeDirectoryWatcher(@Disposes DirectoryWatcher directoryWatcher) {
        Log.info("Dispose DirectoryWatcher");
        try {
            directoryWatcher.close();
        } catch (Exception e) {
            throw new DirectoryWatcherException("Unable to close directory watcher", e);
        }
    }
}
