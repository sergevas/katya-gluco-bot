package dev.sergevas.tool.katya.gluco.bot.xdrip.boundary;

import dev.sergevas.tool.katya.gluco.bot.telegram.control.SensorDataReader;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.SensorReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.telegram.entity.SensorReading;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.GlucoseData;

import java.util.List;

public class InfluxDbSensorDataReader implements SensorDataReader {

    private final SensorReadingMapper<GlucoseData> sensorReadingMapper;
    private final InfluxDbServerApiClient influxDbServerApiClient;

    public InfluxDbSensorDataReader(SensorReadingMapper<GlucoseData> sensorReadingMapper,
                                    InfluxDbServerApiClient influxDbServerApiClient) {
        this.sensorReadingMapper = sensorReadingMapper;
        this.influxDbServerApiClient = influxDbServerApiClient;
    }

    @Override
    public List<SensorReading> read() {
        return sensorReadingMapper.toSensorReadings(influxDbServerApiClient.getReadings());
    }
}
