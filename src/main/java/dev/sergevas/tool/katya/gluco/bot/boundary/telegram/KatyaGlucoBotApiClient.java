package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "katya-glucoBot-api")
public interface KatyaGlucoBotApiClient {

    @POST
    @Path("/bot{token}/sendMessage")
    @Produces(MediaType.TEXT_PLAIN)
    @Retry(delay = 0, maxDuration = 11000, jitter = 500, maxRetries = 2)
    String sendUpdate(@PathParam("token") String token, @QueryParam("chat_id") String chatId, @QueryParam("text") String text);
}
