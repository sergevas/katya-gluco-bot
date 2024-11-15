package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco.parser;

public class LongParseWorker extends ParseWorker<Long> {

    public LongParseWorker(int fromIndex, int binValueLength) {
        super(fromIndex, binValueLength);
    }

    @Override
    public Long unmarshal(byte[] source) {
        long value = 0L;
        for (byte b : source) {
            value <<= 8;
            value |= (b & 0xFF);
        }
        return value;
    }
}
