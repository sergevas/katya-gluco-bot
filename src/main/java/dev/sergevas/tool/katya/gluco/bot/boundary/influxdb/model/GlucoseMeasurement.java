package dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class GlucoseMeasurement {

    private Instant time;
    private double delta;
    private String direction;
    private int filtered;
    private int noise;
    private int rssi;
    private int unfiltered;
    private int valueMgdl;
    private double valueMmol;

    public GlucoseMeasurement() {
    }

    public GlucoseMeasurement(List<Object> values) {
        this.time = Instant.parse(valueOf(values.get(0)));
        this.delta = parseDouble(valueOf(values.get(1)));
        this.direction = valueOf(values.get(2));
        this.filtered = parseInt(valueOf(values.get(3)));
        this.noise = parseInt(valueOf(values.get(4)));
        this.rssi = parseInt(valueOf(values.get(5)));
        this.unfiltered = parseInt(valueOf(values.get(6)));
        this.valueMgdl = parseInt(valueOf(values.get(7)));
        this.valueMmol = parseDouble(valueOf(values.get(8)));
    }

    public Instant getTime() {
        return time;
    }

    public GlucoseMeasurement setTime(Instant time) {
        this.time = time;
        return this;
    }

    public double getDelta() {
        return delta;
    }

    public GlucoseMeasurement setDelta(double delta) {
        this.delta = delta;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public GlucoseMeasurement setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public int getFiltered() {
        return filtered;
    }

    public GlucoseMeasurement setFiltered(int filtered) {
        this.filtered = filtered;
        return this;
    }

    public int getNoise() {
        return noise;
    }

    public GlucoseMeasurement setNoise(int noise) {
        this.noise = noise;
        return this;
    }

    public int getRssi() {
        return rssi;
    }

    public GlucoseMeasurement setRssi(int rssi) {
        this.rssi = rssi;
        return this;
    }

    public int getUnfiltered() {
        return unfiltered;
    }

    public GlucoseMeasurement setUnfiltered(int unfiltered) {
        this.unfiltered = unfiltered;
        return this;
    }

    public int getValueMgdl() {
        return valueMgdl;
    }

    public GlucoseMeasurement setValueMgdl(int valueMgdl) {
        this.valueMgdl = valueMgdl;
        return this;
    }

    public double getValueMmol() {
        return valueMmol;
    }

    public GlucoseMeasurement setValueMmol(double valueMmol) {
        this.valueMmol = valueMmol;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GlucoseMeasurement that = (GlucoseMeasurement) o;
        return Double.compare(delta, that.delta) == 0 && filtered == that.filtered && noise == that.noise
                && rssi == that.rssi && unfiltered == that.unfiltered && valueMgdl == that.valueMgdl
                && Double.compare(valueMmol, that.valueMmol) == 0 && Objects.equals(time, that.time)
                && Objects.equals(direction, that.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, delta, direction, filtered, noise, rssi, unfiltered, valueMgdl, valueMmol);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GlucoseMeasurement.class.getSimpleName() + "[", "]")
                .add("time=" + time)
                .add("delta=" + delta)
                .add("direction='" + direction + "'")
                .add("filtered=" + filtered)
                .add("noise=" + noise)
                .add("rssi=" + rssi)
                .add("unfiltered=" + unfiltered)
                .add("valueMgdl=" + valueMgdl)
                .add("valueMmol=" + valueMmol)
                .toString();
    }
}
