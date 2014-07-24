package com.example.instrumentation;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TimingAspect {

    @Pointcut("execution(* com.example.BarService.doSomeWork())")
    public void logTime() { }

    @Around(value = "logTime()")
    public Object timeExecution(ProceedingJoinPoint pjp) throws Throwable {
        MetricsRegistry registry = Metrics.defaultRegistry();

        Timer timer = registry.newTimer(pjp.getThis().getClass(), pjp.getSignature().getName());
        TimerContext time = timer.time();
        try {
            return pjp.proceed();
        } finally {
            time.stop();

            System.out.println(String.format("min: %s, max: %s, count: %d", timer.min(), timer.max(), timer.count()));
        }


    }
}
