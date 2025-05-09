package dev.sergevas.tool.katya.gluco.bot.support;

import io.smallrye.common.constraint.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class InMemoryLogHandler extends Handler {

    private final Predicate<LogRecord> predicate;

    public InMemoryLogHandler(Predicate<LogRecord> predicate) {
        this.predicate = Assert.checkNotNullParam("predicate", predicate);
    }

    final List<LogRecord> records = new ArrayList<>();

    @Override
    public void publish(LogRecord record) {
        if (predicate.test(record)) {
            records.add(record);
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public Level getLevel() {
        return Level.FINE;
    }

    @Override
    public void close() throws SecurityException {
        this.records.clear();
    }
}
