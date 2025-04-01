package dev.sergevas.tool.katya.gluco.bot.support;

import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.PollsSensorReadingEntity;
import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.Trend;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;

import java.time.LocalDateTime;

public class TestData {

    public static final PollsSensorReading POLLS_SENSOR_READING_1 = new PollsSensorReading(1729851911L,
            LocalDateTime.of(2024, 10, 25, 10, 25, 11),
            101, 54, PollsSensorReading.Trend.FALLING, -1.65f);

    public static final PollsSensorReading POLLS_SENSOR_READING_2 = new PollsSensorReading(1729851971L,
            LocalDateTime.of(2024, 10, 25, 10, 26, 11),
            102, 53, PollsSensorReading.Trend.FALLING, -1.7f);

    public static final PollsSensorReading POLLS_SENSOR_READING_3 = new PollsSensorReading(1729841172L,
            LocalDateTime.of(2024, 10, 25, 10, 26, 12),
            103, 50, PollsSensorReading.Trend.FALLING, -2.1f);

    public static final PollsSensorReadingEntity POLLS_SENSOR_READING_ENTITY_1 = new PollsSensorReadingEntity();

    static {
        POLLS_SENSOR_READING_ENTITY_1.setTimeEpoch(POLLS_SENSOR_READING_1.getTimeEpoch());
        POLLS_SENSOR_READING_ENTITY_1.setTimeLocal(POLLS_SENSOR_READING_1.getTimeLocal());
        POLLS_SENSOR_READING_ENTITY_1.setMinSinceStart(POLLS_SENSOR_READING_1.getMinSinceStart());
        POLLS_SENSOR_READING_ENTITY_1.setGlucose(POLLS_SENSOR_READING_1.getGlucoseMgDl());
        POLLS_SENSOR_READING_ENTITY_1.setTrend(Trend.valueOf(POLLS_SENSOR_READING_1.getTrend().name()));
        POLLS_SENSOR_READING_ENTITY_1.setRateOfChange(POLLS_SENSOR_READING_1.getRateOfChange());
    }
}
