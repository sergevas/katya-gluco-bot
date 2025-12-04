package dev.sergevas.tool.katya.gluco.bot.readings.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.readings.control.ReadingService;
import dev.sergevas.tool.katya.gluco.bot.readings.entity.SensorReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/readings")
public class ReadingsController {

    private final Logger LOG = LoggerFactory.getLogger(ReadingsController.class);

    private final ReadingService readingService;

    public ReadingsController(ReadingService readingService) {
        this.readingService = readingService;
    }

    @GetMapping(value = "/last", produces = MediaType.APPLICATION_JSON_VALUE)
    public SensorReading getLastReading() {
        LOG.info("Enter getLastReading()");
        var lastReadingOpt = readingService.getLastReading();
        //TODO: add proper exception handling
        var reading = lastReadingOpt
                .orElseThrow(() -> new RuntimeException("No reading available"));
        LOG.info("Exit getLastReading(): {}", reading);
        return reading;
    }
}
