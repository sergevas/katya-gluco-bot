package dev.sergevas.tool.katya.gluco.bot.readings.control;

import dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotException;

public class ReadingsNotFoundException extends KatyaGlucoBotException {

    public ReadingsNotFoundException(String message) {
        super(message);
    }
}
