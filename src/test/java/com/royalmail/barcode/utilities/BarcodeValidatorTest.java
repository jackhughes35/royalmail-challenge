package com.royalmail.barcode.utilities;

import com.royalmail.barcode.exception.InvalidCountryCodeException;
import com.royalmail.barcode.exception.InvalidPrefixException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class BarcodeValidatorTest {

    @Autowired
    BarcodeValidator barcodeValidator;

    // Values used in tests
    // Valid
    private final String validInput = "AA473124829GB";
    private final String validInput_withZeroPadding = "AA00473124829GB";
    // Invalid
    private final String invalidInput_invalidCheckDigit = "AA473124828GB";
    private final String invalidInput_invalidLength = "AA47312482GB";
    private final String invalidInput_invalidPrefix = "A3473124829GB";
    private final String invalidInput_codeContainsLetters = "SAA4A3124829GB";
    private final String invalidInput_codeContainsSymbols = "AA47!124829GB";
    private final String invalidInput_countryCodeInvalid = "AA47!124829NZ";

    /**
     * Testing scenario where valid barcode is provided
     * Expected: true
     */
    @Test
    public void testValidBarcode() {
        Assertions.assertTrue(barcodeValidator.validateBarcode(validInput));
    }

    /**
     * Testing scenario where valid barcode is provided with zero-padding
     * Expected: true
     */
    @Test
    public void testValidBarcode_zeroPadding() {
        Assertions.assertTrue(barcodeValidator.validateBarcode(validInput_withZeroPadding));
    }

    /**
     * Testing scenario where invalid barcode is provided - incorrect length
     * Expected:false
     */
    @Test
    public void testInvalidBarcode_Length() {
        Assertions.assertFalse(barcodeValidator.validateBarcode(invalidInput_invalidLength));
    }

    /**
     * Testing scenario where invalid barcode is provided - Invalid prefix
     * Expected: {@link InvalidPrefixException}
     */
    @Test
    public void testInvalidBarcode_Prefix() {
        Assertions.assertThrows(InvalidPrefixException.class,
                () -> barcodeValidator.validateBarcode(invalidInput_invalidPrefix)
        );
    }

    /**
     * Testing scenario where invalid barcode is provided - non-digit character provided
     * Expected: false
     */
    @Test
    public void testInvalidBarcode_SerialNumberContainsSymbols() {
        Assertions.assertFalse(barcodeValidator.validateBarcode(invalidInput_codeContainsSymbols));
    }

    /**
     * Testing scenario where invalid barcode is provided - non-digit character provided
     * Expected: False
     */
    @Test
    public void testInvalidBarcode_SerialNumberContainsLetters() {
        Assertions.assertFalse(barcodeValidator.validateBarcode(invalidInput_codeContainsLetters));
    }

    /**
     * Testing scenario where invalid barcode is provided - Invalid CheckDigit
     * Expected: false
     */
    @Test
    public void testInvalidBarcode_CheckDigit() {
        Assertions.assertFalse(barcodeValidator.validateBarcode(invalidInput_invalidCheckDigit));
    }

    /**
     * Testing scenario where invalid barcode is provided - Invalid CountryCode
     * {@link InvalidCountryCodeException}
     */
    @Test
    public void testInvalidBarcode_CountryCode() {
        Assertions.assertThrows(InvalidCountryCodeException.class,
                () -> barcodeValidator.validateBarcode(invalidInput_countryCodeInvalid)
        );
    }
}
