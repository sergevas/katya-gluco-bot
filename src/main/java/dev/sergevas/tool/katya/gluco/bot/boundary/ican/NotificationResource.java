package dev.sergevas.tool.katya.gluco.bot.boundary.ican;

import io.quarkus.logging.Log;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ican")
public class NotificationResource {

    @POST
    @Consumes(MediaType.WILDCARD)
    @Path("/notify")
    public Response processNotification(String notificationPayload) {
        Log.infof("Have got iCan Notification payload: {%s}", notificationPayload);
        return Response.ok().build();
    }
}
