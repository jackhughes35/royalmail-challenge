package com.royalmail.barcode.utilities;

import com.royalmail.barcode.config.BarcodeConfiguration;
import com.royalmail.barcode.entity.CountryCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BarcodeValidator {

    @Autowired
    BarcodeConfiguration config;

    public boolean validateBarcode(String barcode){
        // Process input: trim whitespace, make uppercase
        barcode = barcode.trim().toUpperCase();

        // Extract country Code
        CountryCode countryCode = CountryCode.valueOf(barcode.substring(barcode.length() - 2));
        if(!config.getValidCountryCodes().contains(countryCode)){
            return false;
        }

        if(null == barcode || barcode.length() != config.getValidBarcodeLength()){
            log.info("Invalid Barcode : {} of length {}, must be of length {}", barcode, barcode.length(), config.getValidBarcodeLength());
            return false;
        }

        // Validate Prefix is only uppercase letters
        if(!barcode.substring(0, 1).matches(config.getPrefixValueRange())){
            // TODO: This is not working
            log.error("ERROR");
            return false;
        }

        // Validate the 8 digit serial number but including the padding..

        // CheckDigit validation

        // Check the value of the country code. Currently only accepting GB
        return true;
    }

    private boolean isValidCheckDigit(){
        int checkDigit = 0;
        int expectedDigit = 0;

        return (checkDigit == expectedDigit);
        // Ignore the first two chars

        // Ignore last two chars

        // Assign the weights 8, 6, 4, 2, 3, 5, 9, 7 to the 8 digits, from left to right

        // Calculate the sum, of each digit multiplied by its weight.

        // Calculate the check digit from:
        // Check digit = 11 - (sum mod 11)

        // If check difit = 10, change to 0
        // If check digit = 11, change to 5
    }

    private boolean isValidPrefix(){

        return false;
    }


}
