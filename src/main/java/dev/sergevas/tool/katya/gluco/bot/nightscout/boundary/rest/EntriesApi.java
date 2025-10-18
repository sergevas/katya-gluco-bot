package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.infra.logging.Loggable;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.SensorDataRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/entries")
public class EntriesApi {

    private final SensorDataRepository sensorDataRepository;

    public EntriesApi(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @POST
    @Consumes({APPLICATION_JSON, TEXT_PLAIN})
    @Loggable(logArguments = true)
    public Response addEntries(@Valid @NotNull List<@Valid Entry> entries) {
        sensorDataRepository.storeNsEntries(NsEntryMapper.toNsEntries(entries));
        return Response.ok().build();
    }

    @GET
    @Path("/all")
    @Produces({APPLICATION_JSON})
    public Response getAllEntries(/*@QueryParam("count") BigDecimal count*/) {
        return Response.ok().entity(NsEntryMapper.toEntries(sensorDataRepository.getAllNsEntries())).build();
    }

    @GET
    @Produces({APPLICATION_JSON})
    public Response getEntries(@QueryParam("find") String find, @QueryParam("count") BigDecimal count) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @DELETE
    public Response remove(@QueryParam("find") String find, @QueryParam("count") BigDecimal count) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
