package dev.sergevas.tool.katya.gluco.bot.infra.logging;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;
import org.jboss.logging.Logger.Level;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {

    @Nonbinding
    boolean logArguments() default false;

    @Nonbinding
    boolean logReturnVal() default false;

    @Nonbinding
    Level level() default Level.INFO;
}
