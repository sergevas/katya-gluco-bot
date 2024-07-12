package dev.sergevas.tool.katya.gluco.bot.boundary.juggluco;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Retry;

//@RegisterRestClient(configKey = "juggluco-webserver-api")
public interface JugglucoWebServerApi {

    @GET
    @Path("/x/stream")
    @Consumes(MediaType.TEXT_PLAIN)
    @Retry(delay = 0, maxDuration = 11000, jitter = 500, maxRetries = 2)
    String getStream();
}
