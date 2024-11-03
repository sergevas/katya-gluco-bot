package dev.sergevas.tool.katya.gluco.bot.boundary.button;

import dev.sergevas.tool.katya.gluco.bot.control.button.ButtonEventUseCase;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/button/events")
public class ButtonEventsResource {

    @Inject
    ButtonEventUseCase buttonEventUseCase;

    @GET
    @Path("/0001")
    public Response processEvent0001Get() {
        Log.info("Received GET. Processing event0001");
        buttonEventUseCase.processEvent0001();
        return Response.ok().build();
    }
}
