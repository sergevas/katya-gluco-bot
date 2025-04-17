package dev.sergevas.tool.katya.gluco.bot.control;

import dev.sergevas.tool.katya.gluco.bot.entity.ICanReading;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LastReadingCacheManager {

    private ICanReading cachedICanReading;

    public boolean checkUpdatesJugglucoStreamReading(ICanReading currentICanReading) {
        boolean isUpdated = !currentICanReading.equals(cachedICanReading);
        if (isUpdated) {
            cachedICanReading = currentICanReading;
        }
        return isUpdated;
    }

    public ICanReading getCachedJugglucoStreamReading() {
        return cachedICanReading;
    }
}
