package com.github.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author Dooby Kim
 * @Date 2024/2/28 下午11:27
 * @Version 1.0
 * @Desc aop 记录调用超时的 service (com.github.service.impl)
 */
@Aspect
@Component
public class LogAspect {

    public static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    private static final long THRESHOLD_TIME = 3000;

    @Around("execution(* com.github.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) {
        log.info("------------ start {}.{} ------------",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());
        final long startTime = System.currentTimeMillis();
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("error", throwable);
        }
        final long endTime = System.currentTimeMillis();
        long takeTime = endTime - startTime;
        if (takeTime > THRESHOLD_TIME) {
            log.warn("------------ take time over threshold : {} ------------", takeTime);
        }
        return proceed;
    }
}
