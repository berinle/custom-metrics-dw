package com.example.instrumentation;

import com.yammer.metrics.annotation.Timed;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class TimedInterceptor implements MethodInterceptor {

    static MethodInterceptor forMethod(MetricsRegistry metricRegistry, Class<?> klass, Method method) {
        final Timed annotation = method.getAnnotation(Timed.class);
        if (annotation != null) {
            final Timer timer = metricRegistry.newTimer(klass, determineName(annotation, klass, method));
            return new TimedInterceptor(timer);
        }
        return null;
    }

    private static String determineName(Timed annotation, Class<?> klass, Method method) {
        if (null != annotation.name() && !annotation.name().isEmpty()) {
            return annotation.name();
        }

        if (annotation.name().isEmpty()) {
            return method.getName();
        }

        return klass.getSimpleName() + "." + annotation.name();
    }

    private final Timer timer;

    private TimedInterceptor(Timer timer) {
        this.timer = timer;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final TimerContext ctx = timer.time();
        try {
            return invocation.proceed();
        } finally {
            ctx.stop();
        }
    }
}
