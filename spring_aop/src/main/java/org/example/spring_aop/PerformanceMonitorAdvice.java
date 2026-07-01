package org.example.spring_aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;

public class PerformanceMonitorAdvice implements MethodInterceptor {
    @Override
    public @Nullable Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("[PERF] " + invocation.getMethod().getName() + " : " + elapsedTime + " ms");
        }
    }
}
