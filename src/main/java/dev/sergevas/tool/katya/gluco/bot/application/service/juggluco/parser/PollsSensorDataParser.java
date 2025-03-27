package dev.sergevas.tool.katya.gluco.bot.application.service.juggluco.parser;

import dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser.FloatParseWorker;
import dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser.IntegerParseWorker;
import dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser.LongParseWorker;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.sergevas.tool.katya.gluco.bot.application.service.shared.transform.Transformers.toLocalDateTime;

@ApplicationScoped
public class PollsSensorDataParser implements SensorDataParser<List<PollsSensorReading>> {

    private static final int DATA_ROW_LENGTH = 20;
    private static final byte[] EMPTY_ROW = new byte[DATA_ROW_LENGTH];

    private LongParseWorker timeLocalParseWorker;
    private IntegerParseWorker minSinceStartParseWorker;
    private IntegerParseWorker glucoseParseWorker;
    private IntegerParseWorker trendParseWorker;
    private FloatParseWorker rateOfChangeParseWorker;

    @PostConstruct
    public void init() {
        Arrays.fill(EMPTY_ROW, (byte) 0x00);
        timeLocalParseWorker = new LongParseWorker(0, 4);
        minSinceStartParseWorker = new IntegerParseWorker(4, 4);
        glucoseParseWorker = new IntegerParseWorker(8, 12);
        trendParseWorker = new IntegerParseWorker(12, 16);
        rateOfChangeParseWorker = new FloatParseWorker(16, 20);
    }

    @Override
    public List<PollsSensorReading> parse(byte[] rawData) {
        int rawDataLength = rawData.length;
        Log.debugf("Enter parse() rowData.length=%d", rawDataLength);
        int startIdx = 0;
        int endIdx = startIdx + DATA_ROW_LENGTH;
        List<PollsSensorReading> sensorReadings = new ArrayList<>();
        while (startIdx < rawDataLength) {
            byte[] buffer = Arrays.copyOfRange(rawData, startIdx, endIdx);
            if (isCompleted(buffer)) {
                break;
            }
            var timeEpoch = timeLocalParseWorker.unmarshal(buffer);
            sensorReadings.add(new PollsSensorReading(
                    timeEpoch,
                    toLocalDateTime(timeLocalParseWorker.unmarshal(buffer)),
                    minSinceStartParseWorker.unmarshal(buffer),
                    glucoseParseWorker.unmarshal(buffer),
                    PollsSensorReading.Trend.fromCode(trendParseWorker.unmarshal(buffer)),
                    rateOfChangeParseWorker.unmarshal(buffer)));
            startIdx += DATA_ROW_LENGTH;
            endIdx += DATA_ROW_LENGTH;
        }
        Log.debug("Exit parse()");
        return sensorReadings;
    }

    public boolean isCompleted(byte[] rawData) {
        return Arrays.equals(EMPTY_ROW, rawData);
    }
}
