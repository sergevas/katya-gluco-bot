package dev.sergevas.tool.katya.gluco.bot;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.logging.Logger;

@ApplicationPath("/")
public class KatyaGlucoBotApp extends Application {

    public static final Logger LOG = Logger.getLogger("dev.sergevas.tool.katya.gluco.bot");
}
