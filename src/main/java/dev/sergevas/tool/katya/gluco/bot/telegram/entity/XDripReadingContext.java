package dev.sergevas.tool.katya.gluco.bot.telegram.entity;

import dev.sergevas.tool.katya.gluco.bot.xdrip.entity.XDripReading;

public record XDripReadingContext(XDripReading reading, TriggerEvent triggerEvent) {
}
