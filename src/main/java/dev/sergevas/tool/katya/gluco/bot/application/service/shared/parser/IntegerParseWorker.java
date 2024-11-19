package dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser;

public class IntegerParseWorker extends ParseWorker<Integer> {

    public IntegerParseWorker(int fromIndex, int binValueLength) {
        super(fromIndex, binValueLength);
    }

    @Override
    public Integer unmarshal(byte[] source) {
        int value = 0;
        var chunk = fetchChunk(source);
        for (int i = chunk.length - 1; i >= 0; i--) {
            value <<= 8;
            value |= (chunk[i] & 0xFF);
        }
        return value;
    }
}
