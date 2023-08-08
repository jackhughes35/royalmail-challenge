package com.royalmail.barcode.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.royalmail.barcode.exception.*;
@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidPrefixException.class,
            InvalidCheckDigitException.class,
            InvalidSerialNumberException.class,
            InvalidCountryCodeException.class})
    public ResponseEntity<String> handleCustomException(RuntimeException e) {
        log.error("Validation error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("An unexpected error occurred: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }

}
