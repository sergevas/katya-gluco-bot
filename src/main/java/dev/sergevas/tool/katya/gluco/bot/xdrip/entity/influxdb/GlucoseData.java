package dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class GlucoseData {

    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public GlucoseData setResults(List<Result> results) {
        this.results = results;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GlucoseData that = (GlucoseData) o;
        return Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(results);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GlucoseData.class.getSimpleName() + "[", "]")
                .add("results=" + results)
                .toString();
    }
}
