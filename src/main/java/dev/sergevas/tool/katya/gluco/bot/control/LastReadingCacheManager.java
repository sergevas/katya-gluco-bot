package dev.sergevas.tool.katya.gluco.bot.control;

import dev.sergevas.tool.katya.gluco.bot.entity.ICanReading;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@ApplicationScoped
public class LastReadingCacheManager {

    private final ReadWriteLock readWriteLock;
    private ICanReading cachedICanReading;

    public LastReadingCacheManager() {
        readWriteLock = new ReentrantReadWriteLock(true);
    }

    public boolean checkAndUpdateIfNew(ICanReading currentICanReading) {
        var isNew = !currentICanReading.equals(getReading());
        if (isNew) {
            updateReading(currentICanReading);
        }
        return isNew;
    }

    public ICanReading getReading() {
        try {
            readWriteLock.readLock().lock();
            return cachedICanReading;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public ICanReading updateReading(ICanReading iCanReading) {
        try {
            readWriteLock.writeLock().lock();
            cachedICanReading = iCanReading;
            return cachedICanReading;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
