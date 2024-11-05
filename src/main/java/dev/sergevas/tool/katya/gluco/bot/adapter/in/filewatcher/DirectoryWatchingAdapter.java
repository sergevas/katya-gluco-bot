package dev.sergevas.tool.katya.gluco.bot.adapter.in.filewatcher;

import dev.sergevas.tool.katya.gluco.bot.application.port.in.filewatcher.ResourceUpdateUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DirectoryWatchingAdapter {

    @Inject
    ResourceUpdateUseCase resourceUpdateUseCase;
}
