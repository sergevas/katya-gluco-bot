package dev.sergevas.tool.katya.gluco.bot.infra.logging;

import io.quarkus.logging.Log;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

@Loggable
@Interceptor
public class LoggingInterceptor {

    private final TraceIdProvider traceIdProvider;

    public LoggingInterceptor(TraceIdProvider traceIdProvider) {
        this.traceIdProvider = traceIdProvider;
    }

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        Object result;
        var targetName = context.getTarget().getClass().getSimpleName();
        var methodName = context.getMethod().getName();
        var traceId = traceIdProvider.getTraceId();
        Loggable annotation = context.getMethod().getAnnotation(Loggable.class);
        if (annotation == null) {
            annotation = context.getTarget().getClass().getAnnotation(Loggable.class);
        }
        if (annotation != null) {
            var startLogMsgBuilder = new StringBuilder("@traceId=");
            startLogMsgBuilder
                    .append(traceId)
                    .append(" @activity=").append(targetName).append("::").append(methodName)
                    .append(" @status=").append("start");
            if (annotation.logArguments()) {
                Object[] parameters = context.getParameters();
                startLogMsgBuilder.append(" @args=")
                        .append(Optional.ofNullable(parameters)
                                .map(Arrays::toString)
                                .orElse("[]"));
            }
            Log.log(annotation.level(), startLogMsgBuilder.toString());
        }
        var started = System.nanoTime();
        try {
            result = context.proceed();
        } catch (Exception e) {
            Log.infof(e, "@traceId=%s @activity=%s::%s @status=error @took=%d @message=%s",
                    traceId, targetName, methodName, getInvocationTime(started), e.getMessage());
            throw e;
        }
        if (annotation != null) {
            var completeLogMsgBuilder = new StringBuilder("@traceId=");
            completeLogMsgBuilder
                    .append(traceId)
                    .append(" @activity=").append(targetName).append("::").append(methodName)
                    .append(" @status=").append("complete")
                    .append(" @took=").append(getInvocationTime(started));
            if (annotation.logReturnVal() && nonNull(result)) {
                completeLogMsgBuilder.append(" @returnVal=").append(result);
            }
            Log.log(annotation.level(), completeLogMsgBuilder.toString());
        }
        return result;
    }

    private long getInvocationTime(long started) {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started);
    }
}
