package dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser;

public class LongParseWorker extends ParseWorker<Long> {

    public LongParseWorker(int fromIndex, int binValueLength) {
        super(fromIndex, binValueLength);
    }

    @Override
    public Long unmarshal(byte[] source) {
        long value = 0L;
        var chunk = fetchChunk(source);
        for (int i = chunk.length - 1; i >= 0; i--) {
            value <<= 8;
            value |= (chunk[i] & 0xFF);
        }
        return value;
    }
}
