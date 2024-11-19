package dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser;

public class FloatParseWorker extends ParseWorker<Float> {

    private final IntegerParseWorker integerParseWorker;

    public FloatParseWorker(int fromIndex, int binValueLength) {
        super(fromIndex, binValueLength);
        integerParseWorker = new IntegerParseWorker(fromIndex, binValueLength);
    }

    @Override
    public Float unmarshal(byte[] source) {
        return Float.intBitsToFloat(integerParseWorker.unmarshal(source));
    }
}
