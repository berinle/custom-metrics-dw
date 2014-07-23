package com.example.instrumentation;

import com.example.AppComponent;
import com.yammer.metrics.core.MetricsRegistry;
import org.aopalliance.intercept.MethodInterceptor;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Set;

public class TimedListener {
    private static final Logger log = LoggerFactory.getLogger(TimedListener.class);

    private final MetricsRegistry metricsRegistry;

    public TimedListener(MetricsRegistry metricsRegistry) {
        this.metricsRegistry = metricsRegistry;
        init();
    }

    private void init() {
        //see: https://code.google.com/p/reflections/
        final Reflections reflections = new Reflections("com.example");
        final Set<Class<?>> klasses = reflections.getTypesAnnotatedWith(AppComponent.class);

        final boolean debug = log.isDebugEnabled();

        for (Class<?> c : klasses) {
            for (Method method: c.getDeclaredMethods()) {
                final MethodInterceptor interceptor = TimedInterceptor.forMethod(metricsRegistry, c, method);
                if (null != interceptor) {
                    if (debug) {
                        log.debug("Added timed interceptor for class: {}, method: {}", c, method.getName());
                    }
                }
            }
        }
    }
}
