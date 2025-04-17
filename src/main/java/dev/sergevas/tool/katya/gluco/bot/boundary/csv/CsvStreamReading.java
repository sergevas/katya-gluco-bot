package dev.sergevas.tool.katya.gluco.bot.boundary.csv;

import com.opencsv.bean.CsvBindByPosition;

import java.util.Objects;

public class CsvStreamReading {

    /*
     * name,tags,time,delta,direction,filtered,noise,rssi,unfiltered,value_mgdl,value_mmol
     * glucose,,1744664860688000000,2.159,Flat,-0,1,100,0,130,7.214927129235995
     */

    @CsvBindByPosition(position = 0)
    private String name;
    @CsvBindByPosition(position = 1)
    private String tags;
    @CsvBindByPosition(position = 2)
    private String time;
    @CsvBindByPosition(position = 3)
    private String delta;
    @CsvBindByPosition(position = 4)
    private String direction;
    @CsvBindByPosition(position = 5)
    private String filtered;
    @CsvBindByPosition(position = 6)
    private String noise;
    @CsvBindByPosition(position = 7)
    private String rssi;
    @CsvBindByPosition(position = 8)
    private String unfiltered;
    @CsvBindByPosition(position = 9)
    private String value_mgdl;
    @CsvBindByPosition(position = 10)
    private String value_mmol;

    public String getName() {
        return name;
    }

    public CsvStreamReading setName(String name) {
        this.name = name;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public CsvStreamReading setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public String getTime() {
        return time;
    }

    public CsvStreamReading setTime(String time) {
        this.time = time;
        return this;
    }

    public String getDelta() {
        return delta;
    }

    public CsvStreamReading setDelta(String delta) {
        this.delta = delta;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public CsvStreamReading setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public String getFiltered() {
        return filtered;
    }

    public CsvStreamReading setFiltered(String filtered) {
        this.filtered = filtered;
        return this;
    }

    public String getNoise() {
        return noise;
    }

    public CsvStreamReading setNoise(String noise) {
        this.noise = noise;
        return this;
    }

    public String getRssi() {
        return rssi;
    }

    public CsvStreamReading setRssi(String rssi) {
        this.rssi = rssi;
        return this;
    }

    public String getUnfiltered() {
        return unfiltered;
    }

    public CsvStreamReading setUnfiltered(String unfiltered) {
        this.unfiltered = unfiltered;
        return this;
    }

    public String getValue_mgdl() {
        return value_mgdl;
    }

    public CsvStreamReading setValue_mgdl(String value_mgdl) {
        this.value_mgdl = value_mgdl;
        return this;
    }

    public String getValue_mmol() {
        return value_mmol;
    }

    public CsvStreamReading setValue_mmol(String value_mmol) {
        this.value_mmol = value_mmol;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CsvStreamReading that = (CsvStreamReading) o;
        return Objects.equals(name, that.name) && Objects.equals(tags, that.tags)
                && Objects.equals(time, that.time) && Objects.equals(delta, that.delta)
                && Objects.equals(direction, that.direction) && Objects.equals(filtered, that.filtered)
                && Objects.equals(noise, that.noise) && Objects.equals(rssi, that.rssi)
                && Objects.equals(unfiltered, that.unfiltered) && Objects.equals(value_mgdl, that.value_mgdl)
                && Objects.equals(value_mmol, that.value_mmol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tags, time, delta, direction, filtered, noise, rssi, unfiltered, value_mgdl, value_mmol);
    }

    @Override
    public String toString() {
        return "CsvStreamReading{" +
                "name='" + name + '\'' +
                ", tags='" + tags + '\'' +
                ", time='" + time + '\'' +
                ", delta='" + delta + '\'' +
                ", direction='" + direction + '\'' +
                ", filtered='" + filtered + '\'' +
                ", noise='" + noise + '\'' +
                ", rssi='" + rssi + '\'' +
                ", unfiltered='" + unfiltered + '\'' +
                ", value_mgdl='" + value_mgdl + '\'' +
                ", value_mmol='" + value_mmol + '\'' +
                '}';
    }
}
