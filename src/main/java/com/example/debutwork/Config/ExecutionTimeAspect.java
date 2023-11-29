package com.example.debutwork.Config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
//@Aspect
@Slf4j
public class ExecutionTimeAspect {
    @Around("execution(* com.example.debutwork.Service..*.*(..))")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        log.info("test execution methode :  ");
        log.info("Method execution time : " + elapsedTime + " milliseconds.");
        return obj;
    }
}
