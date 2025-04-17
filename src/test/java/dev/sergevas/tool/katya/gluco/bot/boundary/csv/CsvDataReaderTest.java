package dev.sergevas.tool.katya.gluco.bot.boundary.csv;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CsvDataReaderTest {

    @Test
    void readToCsv() {
        var rawData = """
                name,tags,time,delta,direction,filtered,noise,rssi,unfiltered,value_mgdl,value_mmol
                glucose,,1744664860688000000,2.159,Flat,-0,1,100,0,130,7.214927129235995
                glucose,,1744665065087000000,-13.209,SingleDown,-0,1,100,0,121,6.715432174135041""";

        var csvStreamReading1 = new CsvStreamReading();
        csvStreamReading1.setName("glucose");
        csvStreamReading1.setTags("");
        csvStreamReading1.setTime("1744664860688000000");
        csvStreamReading1.setDelta("2.159");
        csvStreamReading1.setDirection("Flat");
        csvStreamReading1.setFiltered("-0");
        csvStreamReading1.setNoise("1");
        csvStreamReading1.setRssi("100");
        csvStreamReading1.setUnfiltered("0");
        csvStreamReading1.setValue_mgdl("130");
        csvStreamReading1.setValue_mmol("7.214927129235995");

        var csvStreamReading2 = new CsvStreamReading();
        csvStreamReading2.setName("glucose");
        csvStreamReading2.setTags("");
        csvStreamReading2.setTime("1744665065087000000");
        csvStreamReading2.setDelta("-13.209");
        csvStreamReading2.setDirection("SingleDown");
        csvStreamReading2.setFiltered("-0");
        csvStreamReading2.setNoise("1");
        csvStreamReading2.setRssi("100");
        csvStreamReading2.setUnfiltered("0");
        csvStreamReading2.setValue_mgdl("121");
        csvStreamReading2.setValue_mmol("6.715432174135041");
        var csvDataReader = new CsvDataReader();
        var expected = List.of(csvStreamReading1, csvStreamReading2);
        assertIterableEquals(expected, new CsvDataReader().readToCsv(CsvStreamReading.class, rawData));
    }
}
