package com.royalmail.barcode.utilities;

import com.royalmail.barcode.config.BarcodeConfiguration;
import com.royalmail.barcode.entity.CountryCode;
import com.royalmail.barcode.exception.InvalidPrefixException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class BarcodeValidator {

    @Autowired
    BarcodeConfiguration config;

    public boolean validateBarcode(String barcode){
        // Process input: trim whitespace, make uppercase
        barcode = barcode.trim().toUpperCase();

        // Validate Prefix is only uppercase letters
        Pattern prefixPatter = Pattern.compile(config.getPrefixValueRange());
        if(!prefixPatter.matcher(barcode.substring(0, 2)).matches()){
            log.error("invalid prefix");
            throw new InvalidPrefixException("Invalid prefix provided");
        }

        // Extract country Code: Throws InvalidCountryCodeException
        CountryCode countryCode = mapCountryCode(barcode);
        if(!config.getValidCountryCodes().contains(countryCode)){
            return false;
        }

        // Check barcode Length TODO: Must check for left-padding.
        // Strip out first two digits (Prefix), strip out last two Inputs (Country Code)
        // Separate two, the last of the remaining is the check-digit, the previous is the 8 digit serial code (Left padded zeros)
        String serialNumber = barcode.substring(2, barcode.length() - 3);
        int checkDigit = barcode.charAt(barcode.length() - 3);

        if(null == barcode || barcode.length() != config.getValidBarcodeLength()){
            log.info("Invalid Barcode : {} of length {}, must be of length {}", barcode, barcode.length(), config.getValidBarcodeLength());
            return false;
        }

        // Validate the 8 digit serial number but including the padding..

        // CheckDigit validation
        S10CheckDigitAlgorithmImpl s10Impl = new S10CheckDigitAlgorithmImpl();
//        s10Impl.isValidCheckDigit();
        // Check the value of the country code. Currently only accepting GB
        return true;
    }

    /**
     * Helper method to map the Countrycode from the input Barcode, returning the {@link CountryCode}
     * @param barcode
     * @return the {@link CountryCode} parsed from the input String
     * @throws IllegalArgumentException if invalid mapping to enum occurrs.
     */
    public CountryCode mapCountryCode(String barcode){
        return CountryCode.valueOf(barcode.substring(barcode.length() - 2));
    }


    private boolean isValidPrefix(){

        return false;
    }


}
