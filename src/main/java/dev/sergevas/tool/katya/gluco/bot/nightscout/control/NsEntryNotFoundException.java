package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotException;

public class NsEntryNotFoundException extends KatyaGlucoBotException {

    public NsEntryNotFoundException() {
        super();
    }

    public NsEntryNotFoundException(String message) {
        super(message);
    }

    public NsEntryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NsEntryNotFoundException(Throwable cause) {
        super(cause);
    }

    public NsEntryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
