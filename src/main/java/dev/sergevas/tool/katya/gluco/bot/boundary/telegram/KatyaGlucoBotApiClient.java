package dev.sergevas.tool.katya.gluco.bot.boundary.telegram;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "katya-glucoBot-api")
public interface KatyaGlucoBotApiClient {

    @POST
    @Path("/bot{token}/sendMessage")
    @Produces(MediaType.TEXT_PLAIN)
    String sendUpdate(@PathParam("token") String token, @QueryParam("chat_id") String chatId, @QueryParam("text") String text);
}
