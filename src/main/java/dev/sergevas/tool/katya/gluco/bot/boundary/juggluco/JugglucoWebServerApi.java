package dev.sergevas.tool.katya.gluco.bot.boundary.juggluco;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.Retry;

//@RegisterRestClient(configKey = "juggluco-webserver-api")
public interface JugglucoWebServerApi {

    @GET
    @Path("/query")
    @Consumes("application/csv")
    @Retry(delay = 0, maxDuration = 11000, jitter = 500, maxRetries = 2)
    String getStream();
}
