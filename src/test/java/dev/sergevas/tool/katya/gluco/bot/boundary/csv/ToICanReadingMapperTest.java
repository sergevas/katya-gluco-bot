package dev.sergevas.tool.katya.gluco.bot.boundary.csv;

import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.ToICanReadingMapper;
import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.model.GlucoseData;
import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.model.GlucoseMeasurement;
import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.model.Result;
import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.model.Series;
import dev.sergevas.tool.katya.gluco.bot.entity.ICanReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.FLAT;
import static dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus.SINGLE_DOWN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ToICanReadingMapperTest {

    private GlucoseData glucoseData;
    private ICanReading iCanReading1;
    private ICanReading iCanReading2;
    private ICanReading iCanReading3;

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

        iCanReading1 = new ICanReading(Instant.parse("2025-04-14T21:07:40.688Z"), 7.214927129235995, FLAT);
        iCanReading2 = new ICanReading(Instant.parse("2025-04-17T20:35:05.542Z"), 9.323905828551132, SINGLE_DOWN);
        iCanReading3 = new ICanReading(Instant.parse("2025-04-17T20:41:05.5Z"), 9.323905828552232, FLAT);
    }

    @Test
    void toICanReadingList() {
        assertIterableEquals(List.of(iCanReading1, iCanReading2, iCanReading3), ToICanReadingMapper.toICanReadingList(glucoseData));
    }

    @Test
    void toICanReading() {
        assertEquals(iCanReading1, ToICanReadingMapper.toICanReading(new GlucoseMeasurement(List.of("2025-04-14T21:07:40.688Z",
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
