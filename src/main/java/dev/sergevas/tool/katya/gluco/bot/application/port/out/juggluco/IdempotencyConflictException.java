package dev.sergevas.tool.katya.gluco.bot.application.port.out.juggluco;

import dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotBaseException;

public class IdempotencyConflictException extends KatyaGlucoBotBaseException {


    public <S, T> IdempotencyConflictException(S newValue, T existingValue) {
        super(String.format("Idempotency conflict occurred: new %s, existing: %s", newValue, existingValue));
    }
}
