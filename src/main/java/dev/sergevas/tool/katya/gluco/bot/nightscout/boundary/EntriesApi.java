package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.Entry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/entries")
public class EntriesApi {

    @POST
    @Consumes({APPLICATION_JSON, TEXT_PLAIN})
    public Response addEntries(@Valid @NotNull List<@Valid Entry> entry) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Produces({APPLICATION_JSON})
    public Response entriesGet(@QueryParam("find") String find, @QueryParam("count") BigDecimal count) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{spec}")
    @Produces({APPLICATION_JSON})

    public Response entriesSpecGet(@PathParam("spec") String spec, @QueryParam("find") String find, @QueryParam("count") BigDecimal count) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    public Response remove(@QueryParam("find") String find, @QueryParam("count") BigDecimal count) {
        return Response.ok().entity("magic!").build();
    }
}
