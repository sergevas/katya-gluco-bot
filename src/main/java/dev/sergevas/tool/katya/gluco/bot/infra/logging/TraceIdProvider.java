package dev.sergevas.tool.katya.gluco.bot.infra.logging;

import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class TraceIdProvider {

    private final String traceId = UUID.randomUUID().toString();

    public String getTraceId() {
        return traceId;
    }
}
