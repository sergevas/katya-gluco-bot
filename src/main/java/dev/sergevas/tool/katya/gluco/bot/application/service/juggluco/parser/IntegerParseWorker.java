package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco.parser;

public class IntegerParseWorker extends ParseWorker<Integer> {

    public IntegerParseWorker(int fromIndex, int binValueLength) {
        super(fromIndex, binValueLength);
    }

    @Override
    public Integer unmarshal(byte[] source) {
        int value = 0;
        for (byte b : source) {
            value <<= 8;
            value |= (b & 0xFF);
        }
        return value;
    }
}
