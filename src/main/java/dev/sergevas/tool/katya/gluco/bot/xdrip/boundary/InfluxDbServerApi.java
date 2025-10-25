package dev.sergevas.tool.katya.gluco.bot.xdrip.boundary;

import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.influxdb.GlucoseData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

//@ClientHeaderParam(name = "Authorization", value = "{lookupAuth}")
//@RegisterRestClient(configKey = "influxdb")
public interface InfluxDbServerApi {

    //    @GET
//    @Path("/query")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Retry(delay = 0, maxDuration = 11000, jitter = 500, maxRetries = 2)
    @GetExchange(accept = MediaType.APPLICATION_JSON_VALUE)
    GlucoseData getReadings(@RequestParam("db") String db, @RequestParam("q") String query);

    /*default String lookupAuth() {
        var user = getConfig().getValue("influxdb.username", String.class);
        var password = getConfig().getValue("influxdb.password", String.class);
        return "Basic " + getEncoder().encodeToString((user + ":" + password).getBytes());
    }*/
}
