package org.example.spring_mission_join.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* org.example.spring_mission_join.controller ..*(..))")
    public void controllerLogAspect() {

    }

    @Around("controllerLogAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();

        long startTime = System.currentTimeMillis();
        System.out.println("===> START: " + className + "." + methodName + "()");

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            System.out.println("<=== END: " + className + "." + methodName + "()" + "[" + (endTime - startTime) + " ms]");
        }
    }

}
