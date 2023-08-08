package com.royalmail.barcode.utilities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class BarcodeValidatorTest {

    @Autowired
    BarcodeValidator barcodeValidator;

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

    }

    /**
     * Testing scenario where invalid barcode is provided - Invalid prefix
     * Expected:
     */
    @Test
    public void testInvalidBarcode_Prefix() {

    }

    /**
     * Testing scenario where invalid barcode is provided - non=digit character provided
     * Expected:
     */
    @Test
    public void testInvalidBarcode_SerialNumber() {

    }

    /**
     * Testing scenario where invalid barcode is provided - Invalid CheckDigit
     * Expected:
     */
    @Test
    public void testInvalidBarcode_CheckDigit() {

    }

    /**
     * Testing scenario where invalid barcode is provided - Invalid CountryCode
     * Expected:
     */
    @Test
    public void testInvalidBarcode_CountryCode() {

    }
}
