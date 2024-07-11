package dev.sergevas.tool.katya.gluco.bot.entity;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class JugglucoStreamReading {

    private static final String DELIMITER = " ";

    private final String sensorId;
    private final int nr;
    private final long unixTime;
    private final OffsetDateTime timestamp;
    private final int min;
    private final double reading; // mmol/L
    private final double rate;
    private final ChangeStatus changeStatus;

    public JugglucoStreamReading(String sensorId, int nr, long unixTime, OffsetDateTime timestamp, int min,
                                 double reading, double rate, ChangeStatus changeStatus) {
        this.sensorId = sensorId;
        this.nr = nr;
        this.unixTime = unixTime;
        this.timestamp = timestamp;
        this.min = min;
        this.reading = reading;
        this.rate = rate;
        this.changeStatus = changeStatus;
    }

    public String getSensorId() {
        return sensorId;
    }

    public int getNr() {
        return nr;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public int getMin() {
        return min;
    }

    public double getReading() {
        return reading;
    }

    public double getRate() {
        return rate;
    }

    public ChangeStatus getChangeStatus() {
        return changeStatus;
    }

    public String toFormattedString() {
        return reading +
                DELIMITER +
                changeStatus.getMark();
//                + DELIMITER +
//                timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JugglucoStreamReading that = (JugglucoStreamReading) o;
        return nr == that.nr && unixTime == that.unixTime && min == that.min && Double.compare(reading, that.reading) == 0
                && Double.compare(rate, that.rate) == 0 && Objects.equals(sensorId, that.sensorId)
                && Objects.equals(timestamp, that.timestamp) && changeStatus == that.changeStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorId, nr, unixTime, timestamp, min, reading, rate, changeStatus);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", JugglucoStreamReading.class.getSimpleName() + "[", "]")
                .add("sensorId='" + getSensorId() + "'")
                .add("nr=" + getNr())
                .add("unixTime=" + getUnixTime())
                .add("timestamp=" + getTimestamp())
                .add("min=" + getMin())
                .add("reading=" + getReading())
                .add("rate=" + getRate())
                .add("changeStatus=" + getChangeStatus())
                .toString();
    }
}
