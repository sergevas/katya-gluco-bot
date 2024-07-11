package dev.sergevas.tool.katya.gluco.bot.boundary.juggluco;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "juggluco-webserver-api")
public interface JugglucoWebServerApiClient {

    @GET
    @Path("/x/stream")
    @Consumes(MediaType.TEXT_PLAIN)
    String getStream();
}
