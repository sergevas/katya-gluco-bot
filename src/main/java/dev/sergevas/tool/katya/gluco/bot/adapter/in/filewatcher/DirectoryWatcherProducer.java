package dev.sergevas.tool.katya.gluco.bot.adapter.in.filewatcher;

import io.methvin.watcher.DirectoryWatcher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class DirectoryWatcherProducer {

    @ConfigProperty(name = "filewatcher.basedir")
    String baseDir;

    @Produces
    @ApplicationScoped
    public DirectoryWatcher directoryWatcher() {
        this.watcher = DirectoryWatcher.builder()
                .path(directoryToWatch) // or use paths(directoriesToWatch)
                .listener(event -> {
                    switch (event.eventType()) {
                        case CREATE: /* file created */
                            ;
                            break;
                        case MODIFY: /* file modified */
                            ;
                            break;
                        case DELETE: /* file deleted */
                            ;
                            break;
                    }
                })
                // .fileHashing(false) // defaults to true
                // .logger(logger) // defaults to LoggerFactory.getLogger(DirectoryWatcher.class)
                // .watchService(watchService) // defaults based on OS to either JVM WatchService or the JNA macOS WatchService
                .build();

    }
}
