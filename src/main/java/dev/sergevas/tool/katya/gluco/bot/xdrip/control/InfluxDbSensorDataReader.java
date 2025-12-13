package dev.sergevas.tool.katya.gluco.bot.xdrip.control;

import dev.sergevas.tool.katya.gluco.bot.readings.control.SensorDataReader;
import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;
import dev.sergevas.tool.katya.gluco.bot.xdrip.boundary.InfluxDbServerApiClient;

import java.util.List;

public class InfluxDbSensorDataReader implements SensorDataReader {

    private final FromXDripReadingMapper fromXDripReadingMapper;
    private final InfluxDbServerApiClient influxDbServerApiClient;

    public InfluxDbSensorDataReader(FromXDripReadingMapper fromXDripReadingMapper,
                                    InfluxDbServerApiClient influxDbServerApiClient) {
        this.fromXDripReadingMapper = fromXDripReadingMapper;
        this.influxDbServerApiClient = influxDbServerApiClient;
    }

    @Override
    public List<SensorReading> read() {
        return fromXDripReadingMapper.toSensorReadings(influxDbServerApiClient.getReadings());
    }
}
