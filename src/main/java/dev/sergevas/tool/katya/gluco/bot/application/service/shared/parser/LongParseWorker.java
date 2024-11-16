package dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser;

public class LongParseWorker extends ParseWorker<Long> {

    public LongParseWorker(int fromIndex, int binValueLength) {
        super(fromIndex, binValueLength);
    }

    @Override
    public Long unmarshal(byte[] source) {
        long value = 0L;
        for (int i = source.length - 1; i >= 0; i--) {
            value <<= 8;
            value |= (source[i] & 0xFF);
        }
        return value;
    }
}
