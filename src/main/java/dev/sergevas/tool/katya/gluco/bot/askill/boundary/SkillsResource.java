package dev.sergevas.tool.katya.gluco.bot.askill.boundary;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/skills")
public class SkillsResource {


    @GET
    public String getCurrentReading() {
        return null;
    }
}
