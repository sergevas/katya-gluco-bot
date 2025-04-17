package dev.sergevas.tool.katya.gluco.bot.boundary.csv;

import dev.sergevas.tool.katya.gluco.bot.entity.ICanReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.FLAT;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.SINGLE_DOWN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ToICanReadingMapperTest {

    private CsvStreamReading csvStreamReading1;
    private CsvStreamReading csvStreamReading2;
    private CsvStreamReading csvStreamReading3;
    private ICanReading iCanReading1;
    private ICanReading iCanReading2;
    private ICanReading iCanReading3;

    /*
    * name,tags,time,delta,direction,filtered,noise,rssi,unfiltered,value_mgdl,value_mmol
glucose,,1744664860688000000,2.159,Flat,-0,1,100,0,130,7.214927129235995
glucose,,1744665065087000000,-13.209,SingleDown,-0,1,100,0,121,6.715432174135041
glucose,,1744665245044000000,3.334,Flat,-0,1,100,0,123,6.826431053046364
    * */

    @BeforeEach
    void setup() {
        csvStreamReading1 = new CsvStreamReading();
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

        csvStreamReading2 = new CsvStreamReading();
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

        csvStreamReading3 = new CsvStreamReading();
        csvStreamReading3.setName("glucose");
        csvStreamReading3.setTags("");
        csvStreamReading3.setTime("1744665245044000000");
        csvStreamReading3.setDelta("3.334");
        csvStreamReading3.setDirection("Flat");
        csvStreamReading3.setFiltered("-0");
        csvStreamReading3.setNoise("1");
        csvStreamReading3.setRssi("100");
        csvStreamReading3.setUnfiltered("0");
        csvStreamReading3.setValue_mgdl("123");
        csvStreamReading3.setValue_mmol("6.826431053046364");

        iCanReading1 = new ICanReading(1744664860688000000L, 7.214927129235995, FLAT);
        iCanReading2 = new ICanReading(1744665065087000000L, 6.715432174135041, SINGLE_DOWN);
        iCanReading3 = new ICanReading(1744665245044000000L, 6.826431053046364, FLAT);
    }

    @Test
    void toJugglucoStreamReadingList() {
        assertIterableEquals(List.of(iCanReading1, iCanReading2, iCanReading3),
                ToJugglucoStreamReadingMapper.toJugglucoStreamReadingList(List.of(csvStreamReading1, csvStreamReading2, csvStreamReading3)));
    }

    @Test
    void toJugglucoStreamReading() {
        assertEquals(iCanReading1, ToJugglucoStreamReadingMapper.toJugglucoStreamReading(csvStreamReading1));
    }
}
