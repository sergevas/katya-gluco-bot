package dev.sergevas.tool.katya.gluco.bot.control;

import dev.sergevas.tool.katya.gluco.bot.entity.JugglucoStreamReading;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LastReadingCacheManager {

    private JugglucoStreamReading cachedJugglucoStreamReading;

    public boolean checkUpdatesJugglucoStreamReading(JugglucoStreamReading currentJugglucoStreamReading) {
        boolean isUpdated = !currentJugglucoStreamReading.equals(cachedJugglucoStreamReading);
        if (isUpdated) {
            cachedJugglucoStreamReading = currentJugglucoStreamReading;
        }
        return isUpdated;
    }

    public JugglucoStreamReading getCachedJugglucoStreamReading() {
        return cachedJugglucoStreamReading;
    }
}
