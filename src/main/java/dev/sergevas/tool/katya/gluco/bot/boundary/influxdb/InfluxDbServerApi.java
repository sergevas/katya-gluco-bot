package dev.sergevas.tool.katya.gluco.bot.boundary.influxdb;

import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.model.GlucoseData;
import io.quarkus.rest.client.reactive.ClientBasicAuth;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Retry;

@ClientBasicAuth(username = "${influxdb.username}", password = "${influxdb.password}")
public interface InfluxDbServerApi {

    @GET
    @Path("/query")
    @Consumes(MediaType.APPLICATION_JSON)
    @Retry(delay = 0, maxDuration = 11000, jitter = 500, maxRetries = 2)
    GlucoseData getReadings(@QueryParam("db") String db, @QueryParam("q") String query);
}
