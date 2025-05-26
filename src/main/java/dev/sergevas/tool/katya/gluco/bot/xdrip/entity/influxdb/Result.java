package dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Result {
    private int statement_id;
    private List<Series> series;

    public int getStatement_id() {
        return statement_id;
    }

    public void setStatement_id(int statement_id) {
        this.statement_id = statement_id;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return statement_id == result.statement_id && Objects.equals(series, result.series);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statement_id, series);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Result.class.getSimpleName() + "[", "]")
                .add("statement_id=" + statement_id)
                .add("series=" + series)
                .toString();
    }
}
