package com.royalmail.barcode.exception;

public class InvalidCheckDigitException extends BarcodeValidationException {
    public InvalidCheckDigitException(String message) {
        super(message);
    }
}
