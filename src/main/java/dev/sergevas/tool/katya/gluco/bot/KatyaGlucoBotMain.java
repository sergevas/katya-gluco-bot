package dev.sergevas.tool.katya.gluco.bot;

import jakarta.ws.rs.SeBootstrap;

import java.io.IOException;

public class KatyaGlucoBotMain {

    public static void main(String[] args) throws InterruptedException, IOException {
        SeBootstrap.Configuration configuration = SeBootstrap.Configuration.builder()
                .host("localhost")
                .port(8080)
                .protocol("http")
                .build();
        SeBootstrap.start(RestConfig.class. configuration)
                .

    }
}
