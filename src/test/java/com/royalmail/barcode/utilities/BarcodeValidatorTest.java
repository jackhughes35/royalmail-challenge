package com.royalmail.barcode.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class BarcodeValidatorTest {

    @Autowired
    BarcodeValidator barcodeValidator;

    // Values used in tests
    private final String validInput = "AA473124829GB";
    private final String invalidInput_invalidCheckDigit = "AA473124828GB";
    private final String invalidInput_invalidLength = "AA47312482GB";
    private final String invalidInput_invalidPrefix = "A3473124829GB";
    private final String invalidInput_codeContainsLetters = "SAA4A3124829GB";
    private final String invalidInput_codeContainsSymbols = "AA47!124829GB";
    private final String invalidInput_countryCodeInvalid = "AA47!124829NZ";
    /**
     * Testing scenario where valid barcode is provided
     * Expected:
     */
    @Test
    public void testValidBarcode() {

    }

    /**
     * Testing scenario where invalid barcode is provided - incorrect length
     * Expected:
     */
    @Test
    public void testInvalidBarcode_Length() {
        Assertions.assertFalse(barcodeValidator.validateBarcode(invalidInput_invalidLength));
    }

    /**
     * Testing scenario where invalid barcode is provided - Invalid prefix
     * Expected:
     */
    @Test
    public void testInvalidBarcode_Prefix() {
        Assertions.assertFalse(barcodeValidator.validateBarcode(invalidInput_invalidPrefix));
    }

    /**
     * Testing scenario where invalid barcode is provided - non-digit character provided
     * Expected:
     */
    @Test
    public void testInvalidBarcode_SerialNumber() {
        Assertions.assertFalse(barcodeValidator.validateBarcode(invalidInput_codeContainsSymbols));

    }

    /**
     * Testing scenario where invalid barcode is provided - Invalid CheckDigit
     * Expected:
     */
    @Test
    public void testInvalidBarcode_CheckDigit() {
        Assertions.assertFalse(barcodeValidator.validateBarcode(invalidInput_invalidCheckDigit));
    }

    /**
     * Testing scenario where invalid barcode is provided - Invalid CountryCode
     * Expected:
     */
    @Test
    public void testInvalidBarcode_CountryCode() {
        Assertions.assertFalse(barcodeValidator.validateBarcode(invalidInput_countryCodeInvalid));
    }
}
