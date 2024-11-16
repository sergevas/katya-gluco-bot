package dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser;

import java.util.Arrays;

public abstract class ParseWorker<T> {

    private int fromIndex;
    private int binValueLength;

    public ParseWorker(int fromIndex, int binValueLength) {
        this.fromIndex = fromIndex;
        this.binValueLength = binValueLength;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getBinValueLength() {
        return binValueLength;
    }

    public abstract T unmarshal(byte[] source);

    public byte[] fetchChunk(byte[] source) {
        return Arrays.copyOfRange(source, fromIndex, fromIndex + binValueLength);
    }
}
