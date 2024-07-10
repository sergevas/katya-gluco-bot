package dev.sergevas.tool.katya.gluco.bot.boundary.csv;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CsvDataReaderTest {

    @Test
    void readToCsv() {
        var rawData = """
                3MH00Y1F110	879	1720609087	2024-07-10-13:58:07	3	13801	3.1	-1.64	FALLING
                3MH00Y1F110	880	1720611989	2024-07-10-14:46:29	3	13849	7.2	+0.81	STABLE""";
        var csvStreamReading1 = new CsvStreamReading();
        csvStreamReading1.setSensorId("3MH00Y1F110");
        csvStreamReading1.setNr("879");
        csvStreamReading1.setUnixTime("1720609087");
        csvStreamReading1.setTimestamp("2024-07-10-13:58:07");
        csvStreamReading1.setTz("3");
        csvStreamReading1.setMin("13801");
        csvStreamReading1.setReading("3.1");
        csvStreamReading1.setRate("-1.64");
        csvStreamReading1.setChangeLabel("FALLING");

        var csvStreamReading2 = new CsvStreamReading();
        csvStreamReading2.setSensorId("3MH00Y1F110");
        csvStreamReading2.setNr("880");
        csvStreamReading2.setUnixTime("1720611989");
        csvStreamReading2.setTimestamp("2024-07-10-14:46:29");
        csvStreamReading2.setTz("3");
        csvStreamReading2.setMin("13849");
        csvStreamReading2.setReading("7.2");
        csvStreamReading2.setRate("+0.81");
        csvStreamReading2.setChangeLabel("STABLE");
        var csvDataReader = new CsvDataReader();
        var expected = List.of(csvStreamReading1, csvStreamReading2);
        assertIterableEquals(expected, new CsvDataReader().readToCsv(CsvStreamReading.class, rawData));
    }
}
