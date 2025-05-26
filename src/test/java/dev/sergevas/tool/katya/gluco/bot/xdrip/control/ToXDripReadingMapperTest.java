package dev.sergevas.tool.katya.gluco.bot.xdrip.control;

import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.GlucoseData;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.GlucoseMeasurement;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.Result;
import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.Series;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.FLAT;
import static dev.sergevas.tool.katya.gluco.bot.xdrip.entity.ChangeStatus.SINGLE_DOWN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ToXDripReadingMapperTest {

    private GlucoseData glucoseData;
    private XDripReading XDripReading1;
    private XDripReading XDripReading2;
    private XDripReading XDripReading3;

    /*
    {
	"results": [
		{
			"statement_id": 0,
			"series": [
				{
					"name": "glucose",
					"columns": [
						"time",
						"delta",
						"direction",
						"filtered",
						"noise",
						"rssi",
						"unfiltered",
						"value_mgdl",
						"value_mmol"
					],
					"values": [
						[
							"2025-04-14T21:07:40.688Z",
							2.159,
							"Flat",
							0,
							1,
							100,
							0,
							130,
							7.214927129235995
						],
						[
							"2025-04-17T20:35:05.542Z",
							3.334,
							"Flat",
							0,
							1,
							100,
							0,
							168,
							9.323905828551132
						],
						[
							"2025-04-17T20:41:05.5Z",
							0,
							"Flat",
							0,
							1,
							100,
							0,
							168,
							9.323905828551132
						]
					]
				}
			]
		}
	]
}
*/

    @BeforeEach
    void setup() {
        var series = new Series();
        series.setName("glucose");
        series.setColumns(List.of("time", "delta", "direction", "filtered", "noise", "rssi",
                "unfiltered", "value_mgdl", "value_mmol"));
        series.setValues(List.of(
                List.of("2025-04-14T21:07:40.688Z",
                        2.159,
                        "Flat",
                        0,
                        1,
                        100,
                        0,
                        130,
                        7.214927129235995),
                List.of("2025-04-17T20:35:05.542Z",
                        3.334,
                        "SingleDown",
                        0,
                        1,
                        100,
                        0,
                        168,
                        9.323905828551132),
                List.of("2025-04-17T20:41:05.5Z",
                        0,
                        "Flat",
                        0,
                        1,
                        100,
                        0,
                        168,
                        9.323905828552232)
        ));
        var result = new Result();
        result.setStatement_id(0);
        result.setSeries(List.of(series));
        glucoseData = new GlucoseData();
        glucoseData.setResults(List.of(result));

        XDripReading1 = new XDripReading(Instant.parse("2025-04-14T21:07:40.688Z"), 7.214927129235995, FLAT);
        XDripReading2 = new XDripReading(Instant.parse("2025-04-17T20:35:05.542Z"), 9.323905828551132, SINGLE_DOWN);
        XDripReading3 = new XDripReading(Instant.parse("2025-04-17T20:41:05.5Z"), 9.323905828552232, FLAT);
    }

    @Test
    void toXDripReadingList() {
        assertIterableEquals(List.of(XDripReading1, XDripReading2, XDripReading3), ToXDripReadingMapper.toXDripReadingList(glucoseData));
    }

    @Test
    void givenNoSeries_thenShouldReturnEmptyXDripReadingList() {
        var result = new Result();
        result.setStatement_id(0);
        var glucoseData = new GlucoseData();
        glucoseData.setResults(List.of(result));
        assertIterableEquals(List.of(), ToXDripReadingMapper.toXDripReadingList(glucoseData));
    }

    @Test
    void toXDripReading() {
        assertEquals(XDripReading1, ToXDripReadingMapper.toXDripReading(new GlucoseMeasurement(List.of("2025-04-14T21:07:40.688Z",
                2.159,
                "Flat",
                0,
                1,
                100,
                0,
                130,
                7.214927129235995))));
    }
}
