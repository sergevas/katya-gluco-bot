package dev.sergevas.tool.katya.gluco.bot.boundary.influxdb;

import dev.sergevas.tool.katya.gluco.bot.boundary.influxdb.model.GlucoseData;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static java.util.Base64.getEncoder;
import static org.eclipse.microprofile.config.ConfigProvider.getConfig;

@ClientHeaderParam(name = "Authorization", value = "{lookupAuth}")
@RegisterRestClient(configKey = "influxdb")
public interface InfluxDbServerApi {

    @GET
    @Path("/query")
    @Consumes(MediaType.APPLICATION_JSON)
    @Retry(delay = 0, maxDuration = 11000, jitter = 500, maxRetries = 2)
    GlucoseData getReadings(@QueryParam("db") String db, @QueryParam("q") String query);

    default String lookupAuth() {
        var user = getConfig().getValue("influxdb.username", String.class);
        var password = getConfig().getValue("influxdb.password", String.class);
        return "Basic " + getEncoder().encodeToString((user + ":" + password).getBytes());
    }
}
