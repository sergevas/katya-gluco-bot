package dev.sergevas.tool.katya.gluco.bot.adapter.in.filewatcher;

import io.methvin.watcher.DirectoryWatcher;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.file.Path;

public class DirectoryWatcherProducer {

    @ConfigProperty(name = "filewatcher.basedir")
    String baseDir;

    @Produces
    @ApplicationScoped
    public DirectoryWatcher directoryWatcher() {
        Log.info("Produce DirectoryWatcher");
        try {
            return DirectoryWatcher.builder()
                    .path(Path.of(baseDir)) // or use paths(directoriesToWatch)
                    .listener(event -> {
                        switch (event.eventType()) {
                            case CREATE -> Log.infof("Create event: %s", event);
                            case MODIFY -> Log.infof("Modify event: %s", event);
                        }
                    })
                    // .fileHashing(false) // defaults to true
                    // .logger(logger) // defaults to LoggerFactory.getLogger(DirectoryWatcher.class)
                    // .watchService(watchService) // defaults based on OS to either JVM WatchService or the JNA macOS WatchService
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
