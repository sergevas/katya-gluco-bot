package dev.sergevas.tool.katya.gluco.bot.boundary.csv;

import com.opencsv.bean.CsvBindByPosition;

import java.util.Objects;
import java.util.StringJoiner;


public class CsvStreamReading {

    /*
     * Sensorid	nr	UnixTime	YYYY-mm-dd-HH:MM:SS	TZ	Min	mmol/L	Rate	ChangeLabel
     * 3MH00Y1F110	882	1720612291	2024-07-10-14:51:31	3	13854	6.8	+0.49	STABLE
     */

    @CsvBindByPosition(position = 0)
    private String sensorId;
    @CsvBindByPosition(position = 1)
    private String nr;
    @CsvBindByPosition(position = 2)
    private String unixTime;
    @CsvBindByPosition(position = 3)
    private String timestamp;
    @CsvBindByPosition(position = 4)
    private String tz;
    @CsvBindByPosition(position = 5)
    private String min;
    @CsvBindByPosition(position = 6)
    private String reading;
    @CsvBindByPosition(position = 7)
    private String rate;
    @CsvBindByPosition(position = 8)
    private String changeLabel;

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(String unixTime) {
        this.unixTime = unixTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getChangeLabel() {
        return changeLabel;
    }

    public void setChangeLabel(String changeLabel) {
        this.changeLabel = changeLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CsvStreamReading that = (CsvStreamReading) o;
        return Objects.equals(sensorId, that.sensorId) && Objects.equals(nr, that.nr) && Objects.equals(unixTime, that.unixTime)
                && Objects.equals(timestamp, that.timestamp) && Objects.equals(tz, that.tz) && Objects.equals(min, that.min)
                && Objects.equals(reading, that.reading) && Objects.equals(rate, that.rate) && Objects.equals(changeLabel, that.changeLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorId, nr, unixTime, timestamp, tz, min, reading, rate, changeLabel);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CsvStreamReading.class.getSimpleName() + "[", "]")
                .add("sensorId='" + getSensorId() + "'")
                .add("nr='" + getNr() + "'")
                .add("unixTime='" + getUnixTime() + "'")
                .add("timestamp='" + getTimestamp() + "'")
                .add("tz='" + getTz() + "'")
                .add("min='" + getMin() + "'")
                .add("reading='" + getReading() + "'")
                .add("rate='" + getRate() + "'")
                .add("changeLabel='" + getChangeLabel() + "'")
                .toString();
    }
}
