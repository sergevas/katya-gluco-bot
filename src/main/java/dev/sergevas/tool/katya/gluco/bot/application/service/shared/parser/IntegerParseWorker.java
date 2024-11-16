package dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser;

public class IntegerParseWorker extends ParseWorker<Integer> {

    public IntegerParseWorker(int fromIndex, int binValueLength) {
        super(fromIndex, binValueLength);
    }

    @Override
    public Integer unmarshal(byte[] source) {
        int value = 0;
        for (int i = source.length - 1; i >= 0; i--) {
            value <<= 8;
            value |= (source[i] & 0xFF);
        }
        return value;
    }
}
