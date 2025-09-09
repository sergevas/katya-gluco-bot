package dev.sergevas.tool.katya.gluco.bot.nightscout.entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.Valid;

import java.util.Objects;
import java.util.StringJoiner;

public class Entry {

    private String type;
    private String dateString;
    private Long date;
    private Integer sgv;
    private String direction;
    private Integer noise;
    private Integer filtered;
    private Integer unfiltered;
    private Integer rssi;

    /**
     * sgv, mbg, cal, etc
     **/
    public Entry type(String type) {
        this.type = type;
        return this;
    }

    @JsonbProperty("type")
    public String getType() {
        return type;
    }

    @JsonbProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * dateString, MUST be ISO &#x60;8601&#x60; format date parseable by JavaScript Date()
     **/
    public Entry dateString(String dateString) {
        this.dateString = dateString;
        return this;
    }

    @JsonbProperty("dateString")
    public String getDateString() {
        return dateString;
    }

    @JsonbProperty("dateString")
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

    @JsonbProperty("date")
    @Valid
    public Long getDate() {
        return date;
    }

    @JsonbProperty("date")
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


    @JsonbProperty("sgv")
    @Valid
    public Integer getSgv() {
        return sgv;
    }

    @JsonbProperty("sgv")
    public void setSgv(Integer sgv) {
        this.sgv = sgv;
    }

    /**
     * Direction of glucose trend reported by CGM. (only available for sgv types)
     **/
    public Entry direction(String direction) {
        this.direction = direction;
        return this;
    }

    @JsonbProperty("direction")
    public String getDirection() {
        return direction;
    }

    @JsonbProperty("direction")
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

    @JsonbProperty("noise")
    @Valid
    public Integer getNoise() {
        return noise;
    }

    @JsonbProperty("noise")
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

    @JsonbProperty("filtered")
    @Valid
    public Integer getFiltered() {
        return filtered;
    }

    @JsonbProperty("filtered")
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

    @JsonbProperty("unfiltered")
    @Valid
    public Integer getUnfiltered() {
        return unfiltered;
    }

    @JsonbProperty("unfiltered")
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

    @JsonbProperty("rssi")
    @Valid
    public Integer getRssi() {
        return rssi;
    }

    @JsonbProperty("rssi")
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
                Objects.equals(this.dateString, entry.dateString) &&
                Objects.equals(this.date, entry.date) &&
                Objects.equals(this.sgv, entry.sgv) &&
                Objects.equals(this.direction, entry.direction) &&
                Objects.equals(this.noise, entry.noise) &&
                Objects.equals(this.filtered, entry.filtered) &&
                Objects.equals(this.unfiltered, entry.unfiltered) &&
                Objects.equals(this.rssi, entry.rssi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, dateString, date, sgv, direction, noise, filtered, unfiltered, rssi);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Entry.class.getSimpleName() + "[", "]")
                .add("type='" + type + "'")
                .add("dateString='" + dateString + "'")
                .add("date=" + date)
                .add("sgv=" + sgv)
                .add("direction='" + direction + "'")
                .add("noise=" + noise)
                .add("filtered=" + filtered)
                .add("unfiltered=" + unfiltered)
                .add("rssi=" + rssi)
                .toString();
    }
}
