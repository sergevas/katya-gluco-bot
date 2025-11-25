package dev.sergevas.tool.katya.gluco.bot.xdrip.boundary;

import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.GlucoseData;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.service.annotation.GetExchange;

import java.io.IOException;

public interface InfluxDbServerApiClient {

    @GetExchange
    @Retryable(includes = {IOException.class}, maxRetries = 2, maxDelay = 5000, multiplier = 2.0, jitter = 500)
    GlucoseData getReadings();
}
