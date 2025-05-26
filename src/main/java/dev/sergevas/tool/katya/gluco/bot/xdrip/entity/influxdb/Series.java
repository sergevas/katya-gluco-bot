package dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Series {
    private String name;
    private List<String> columns;
    private List<List<Object>> values;  // Каждый элемент values — это List<Object>

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<List<Object>> getValues() {
        return values;
    }

    public void setValues(List<List<Object>> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Series series = (Series) o;
        return Objects.equals(name, series.name) && Objects.equals(columns, series.columns) && Objects.equals(values, series.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, columns, values);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Series.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("columns=" + columns)
                .add("values=" + values)
                .toString();
    }
}
