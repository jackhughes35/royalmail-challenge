package com.royalmail.barcode.aspect;

import com.royalmail.barcode.exception.InvalidCountryCodeException;
import com.royalmail.barcode.exception.InvalidPrefixException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ValidatorExceptionHandlingAspect {

    @Around("execution(* com.royalmail.barcode.utilities.BarcodeValidator.validateBarcode(..))  && args(barcode,..)")
    public Object handelLogging(ProceedingJoinPoint joinPoint, String barcode) throws Throwable {
        // TODO: Should thius be thrown from the specific method?
        try {
            return joinPoint.proceed();
        }
        catch (IllegalArgumentException e) {
        log.info("Message :: {} Incorrect Country code provided {}", e.getLocalizedMessage(), barcode.substring(barcode.length() - 2));
        throw new InvalidCountryCodeException("Invalid Prefix provided");
        }
    }

}
