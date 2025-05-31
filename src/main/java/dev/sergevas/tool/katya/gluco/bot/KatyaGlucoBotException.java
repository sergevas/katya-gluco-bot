package dev.sergevas.tool.katya.gluco.bot;

public class KatyaGlucoBotException extends RuntimeException {

    public KatyaGlucoBotException() {
    }

    public KatyaGlucoBotException(String message) {
        super(message);
    }

    public KatyaGlucoBotException(String message, Throwable cause) {
        super(message, cause);
    }

    public KatyaGlucoBotException(Throwable cause) {
        super(cause);
    }

    public KatyaGlucoBotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
