package com.royalmail.barcode.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.royalmail.barcode.controller.BarcodeValidationController.*(..))")
    public void controllerPointcut(){
    }

//    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
//    public void logAfterReturning(JoinPoint joinPoint, Object result) {
//        String methodName = joinPoint.getSignature().getName();
//        String className = joinPoint.getTarget().getClass().getSimpleName();
//        String logMessage = String.format("Method '%s' in class '%s' returned: %s", methodName, className, result);
//        log.info(logMessage);
//    }
}
