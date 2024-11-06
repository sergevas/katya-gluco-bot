package dev.sergevas.tool.katya.gluco.bot.application;

public class KatyaGlucoBotBaseException extends RuntimeException {

    public KatyaGlucoBotBaseException() {
    }

    public KatyaGlucoBotBaseException(String message) {
        super(message);
    }

    public KatyaGlucoBotBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public KatyaGlucoBotBaseException(Throwable cause) {
        super(cause);
    }

    public KatyaGlucoBotBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
