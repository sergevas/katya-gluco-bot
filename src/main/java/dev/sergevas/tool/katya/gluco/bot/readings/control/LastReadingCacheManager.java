package dev.sergevas.tool.katya.gluco.bot.readings.control;

import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LastReadingCacheManager {

    private final ReadWriteLock readWriteLock;
    private SensorReading cachedSensorReading;

    public LastReadingCacheManager() {
        readWriteLock = new ReentrantReadWriteLock(true);
    }

    public boolean checkAndUpdateIfNew(SensorReading currentSensorReading) {
        var isNew = !currentSensorReading.equals(getReading());
        if (isNew) {
            updateReading(currentSensorReading);
        }
        return isNew;
    }

    public SensorReading getReading() {
        try {
            readWriteLock.readLock().lock();
            return cachedSensorReading;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void updateReading(SensorReading SensorReading) {
        try {
            readWriteLock.writeLock().lock();
            cachedSensorReading = SensorReading;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
