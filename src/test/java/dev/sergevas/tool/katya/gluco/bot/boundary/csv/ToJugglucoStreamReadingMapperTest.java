package dev.sergevas.tool.katya.gluco.bot.boundary.csv;

import dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.entity.JugglucoStreamReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ToJugglucoStreamReadingMapperTest {

    private CsvStreamReading csvStreamReading1;
    private CsvStreamReading csvStreamReading2;
    private CsvStreamReading csvStreamReading3;
    private JugglucoStreamReading jugglucoStreamReading1;
    private JugglucoStreamReading jugglucoStreamReading2;
    private JugglucoStreamReading jugglucoStreamReading3;

    @BeforeEach
    void setup() {
        csvStreamReading1 = new CsvStreamReading();
        csvStreamReading1.setSensorId("3MH00Y1F110");
        csvStreamReading1.setNr("879");
        csvStreamReading1.setUnixTime("1720609087");
        csvStreamReading1.setTimestamp("2024-07-10-13:58:07");
        csvStreamReading1.setTz("3");
        csvStreamReading1.setMin("13801");
        csvStreamReading1.setReading("3.1");
        csvStreamReading1.setRate("-1.64");
        csvStreamReading1.setChangeLabel("FALLING");

        csvStreamReading2 = new CsvStreamReading();
        csvStreamReading2.setSensorId("3MH00Y1F110");
        csvStreamReading2.setNr("880");
        csvStreamReading2.setUnixTime("1720611989");
        csvStreamReading2.setTimestamp("2024-07-10-14:46:29");
        csvStreamReading2.setTz("3");
        csvStreamReading2.setMin("13849");
        csvStreamReading2.setReading("7.2");
        csvStreamReading2.setRate("+0.81");
        csvStreamReading2.setChangeLabel("STABLE");

        csvStreamReading3 = new CsvStreamReading();
        csvStreamReading3.setSensorId("3MH00Y1F3PH");
        csvStreamReading3.setNr("31");
        csvStreamReading3.setUnixTime("1720989715");
        csvStreamReading3.setTimestamp("2024-07-14-23:41:55");
        csvStreamReading3.setTz("3");
        csvStreamReading3.setMin("102");
        csvStreamReading3.setReading("7.6");
        csvStreamReading3.setRate("-0.44");
        csvStreamReading3.setChangeLabel("STABLE");

        jugglucoStreamReading1 = new JugglucoStreamReading(
                "3MH00Y1F110",
                879,
                1720609087,
                OffsetDateTime.parse("2024-07-10T13:58:07+03:00"),
                13801,
                3.1,
                -1.64,
                ChangeStatus.FALLING
        );

        jugglucoStreamReading2 = new JugglucoStreamReading(
                "3MH00Y1F110",
                880,
                1720611989,
                OffsetDateTime.parse("2024-07-10T14:46:29+03:00"),
                13849,
                7.2,
                0.81,
                ChangeStatus.STABLE
        );

        jugglucoStreamReading3 = new JugglucoStreamReading(
                "3MH00Y1F3PH",
                31,
                1720989715,
                OffsetDateTime.parse("2024-07-14T23:41:55+03:00"),
                102,
                7.6,
                -0.44,
                ChangeStatus.STABLE
        );
    }

    @Test
    void toJugglucoStreamReadingList() {
        assertIterableEquals(List.of(jugglucoStreamReading1, jugglucoStreamReading2, jugglucoStreamReading3),
                ToJugglucoStreamReadingMapper.toJugglucoStreamReadingList(List.of(csvStreamReading1, csvStreamReading2, csvStreamReading3)));
    }

    @Test
    void toJugglucoStreamReading() {
        assertEquals(jugglucoStreamReading1, ToJugglucoStreamReadingMapper.toJugglucoStreamReading(csvStreamReading1));
    }
}
