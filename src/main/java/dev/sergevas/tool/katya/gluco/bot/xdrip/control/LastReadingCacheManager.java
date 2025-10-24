package dev.sergevas.tool.katya.gluco.bot.xdrip.control;

import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LastReadingCacheManager {

    private final ReadWriteLock readWriteLock;
    private XDripReading cachedXDripReading;

    public LastReadingCacheManager() {
        readWriteLock = new ReentrantReadWriteLock(true);
    }

    public boolean checkAndUpdateIfNew(XDripReading currentXDripReading) {
        var isNew = !currentXDripReading.equals(getReading());
        if (isNew) {
            updateReading(currentXDripReading);
        }
        return isNew;
    }

    public XDripReading getReading() {
        try {
            readWriteLock.readLock().lock();
            return cachedXDripReading;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public XDripReading updateReading(XDripReading XDripReading) {
        try {
            readWriteLock.writeLock().lock();
            cachedXDripReading = XDripReading;
            return cachedXDripReading;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
