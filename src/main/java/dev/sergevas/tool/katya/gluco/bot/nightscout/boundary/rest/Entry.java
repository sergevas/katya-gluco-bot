package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;

import java.util.Objects;
import java.util.StringJoiner;

public class Entry {

    private String type;
    private String device;
    private String dateString;
    private Long date;
    private Integer sgv;
    private Double delta;
    private String direction;
    private Integer noise;
    private Integer filtered;
    private Integer unfiltered;
    private Integer rssi;

    public Entry() {
        super();
    }

    public Entry(String type, String device, String dateString, Long date, Integer sgv, Double delta, String direction,
                 Integer noise, Integer filtered, Integer unfiltered, Integer rssi) {
        super();
        this.type = type;
        this.device = device;
        this.dateString = dateString;
        this.date = date;
        this.sgv = sgv;
        this.delta = delta;
        this.direction = direction;
        this.noise = noise;
        this.filtered = filtered;
        this.unfiltered = unfiltered;
        this.rssi = rssi;
    }

    /**
     * sgv, mbg, cal, etc
     **/
    public Entry type(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public Entry device(String device) {
        this.type = type;
        return this;
    }

    @JsonProperty("device")
    public String getDevice() {
        return device;
    }

    @JsonProperty("device")
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * sgvTime, MUST be ISO &#x60;8601&#x60; format epochTime parseable by JavaScript Date()
     **/
    public Entry dateString(String dateString) {
        this.dateString = dateString;
        return this;
    }

    @JsonProperty("dateString")
    public String getDateString() {
        return dateString;
    }

    @JsonProperty("dateString")
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    /**
     * Epoch
     **/
    public Entry date(Long date) {
        this.date = date;
        return this;
    }

    @JsonProperty("date")
    @Valid
    public Long getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(Long date) {
        this.date = date;
    }

    /**
     * The glucose reading. (only available for sgv types)
     **/
    public Entry sgv(Integer sgv) {
        this.sgv = sgv;
        return this;
    }

    @JsonProperty("sgv")
    @Valid
    public Integer getSgv() {
        return sgv;
    }

    @JsonProperty("sgv")
    public void setSgv(Integer sgv) {
        this.sgv = sgv;
    }

    public Entry delta(Double delta) {
        this.delta = delta;
        return this;
    }

    @JsonProperty("delta")
    @Valid
    public Double getDelta() {
        return delta;
    }

    @JsonProperty("delta")
    public void setDelta(Double delta) {
        this.delta = delta;
    }

    /**
     * Direction of glucose trend reported by CGM. (only available for sgv types)
     **/
    public Entry direction(String direction) {
        this.direction = direction;
        return this;
    }

    @JsonProperty("direction")
    public String getDirection() {
        return direction;
    }

    @JsonProperty("direction")
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Noise level at time of reading. (only available for sgv types)
     **/
    public Entry noise(Integer noise) {
        this.noise = noise;
        return this;
    }

    @JsonProperty("noise")
    @Valid
    public Integer getNoise() {
        return noise;
    }

    @JsonProperty("noise")
    public void setNoise(Integer noise) {
        this.noise = noise;
    }

    /**
     * The raw filtered value directly from CGM transmitter. (only available for sgv types)
     **/
    public Entry filtered(Integer filtered) {
        this.filtered = filtered;
        return this;
    }

    @JsonProperty("filtered")
    @Valid
    public Integer getFiltered() {
        return filtered;
    }

    @JsonProperty("filtered")
    public void setFiltered(Integer filtered) {
        this.filtered = filtered;
    }

    /**
     * The raw unfiltered value directly from CGM transmitter. (only available for sgv types)
     **/
    public Entry unfiltered(Integer unfiltered) {
        this.unfiltered = unfiltered;
        return this;
    }

    @JsonProperty("unfiltered")
    @Valid
    public Integer getUnfiltered() {
        return unfiltered;
    }

    @JsonProperty("unfiltered")
    public void setUnfiltered(Integer unfiltered) {
        this.unfiltered = unfiltered;
    }

    /**
     * The signal strength from CGM transmitter. (only available for sgv types)
     **/
    public Entry rssi(Integer rssi) {
        this.rssi = rssi;
        return this;
    }

    @JsonProperty("rssi")
    @Valid
    public Integer getRssi() {
        return rssi;
    }

    @JsonProperty("rssi")
    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entry entry = (Entry) o;
        return Objects.equals(this.type, entry.type) &&
                Objects.equals(this.device, entry.device) &&
                Objects.equals(this.dateString, entry.dateString) &&
                Objects.equals(this.date, entry.date) &&
                Objects.equals(this.sgv, entry.sgv) &&
                Objects.equals(this.delta, entry.delta) &&
                Objects.equals(this.direction, entry.direction) &&
                Objects.equals(this.noise, entry.noise) &&
                Objects.equals(this.filtered, entry.filtered) &&
                Objects.equals(this.unfiltered, entry.unfiltered) &&
                Objects.equals(this.rssi, entry.rssi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, device, dateString, date, sgv, delta, direction, noise, filtered, unfiltered, rssi);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Entry.class.getSimpleName() + "[", "]")
                .add("type='" + type + "'")
                .add("device='" + device + "'")
                .add("sgvTime='" + dateString + "'")
                .add("epochTime=" + date)
                .add("sgv=" + sgv)
                .add("delta=" + delta)
                .add("direction='" + direction + "'")
                .add("noise=" + noise)
                .add("filtered=" + filtered)
                .add("unfiltered=" + unfiltered)
                .add("rssi=" + rssi)
                .toString();
    }
}
