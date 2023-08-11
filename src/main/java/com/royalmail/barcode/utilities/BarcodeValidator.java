package com.royalmail.barcode.utilities;

import com.royalmail.barcode.config.BarcodeConfiguration;
import com.royalmail.barcode.entity.CountryCode;
import com.royalmail.barcode.exception.InvalidCountryCodeException;
import com.royalmail.barcode.exception.InvalidPrefixException;
import com.royalmail.barcode.exception.InvalidSerialNumberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
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

        // TODO: Take the logging for each failure and put in the exception handler
        // Validate Prefix is only uppercase letters
        isValidPrefix(barcode);

        // Extract country Code: Throws InvalidCountryCodeException
        isValidCountryCode(barcode);

        // Validate the 8 digit serial number but including the padding..
        // Separate two, the last of the remaining is the check-digit, the previous is the 8 digit serial code (Left padded zeros)
        Integer serialNumber = processBarcode(barcode);
        int checkDigit = barcode.charAt(barcode.length() - 3);

        // CheckDigit validation
        CheckDigitAlgorithm s10Impl = new S10CheckDigitAlgorithmImpl();
        return s10Impl.isValidCheckDigit(serialNumber, checkDigit);
    }

    /**
     * Helper method to map the Countrycode from the input Barcode, returning the {@link CountryCode}
     * @param barcode
     * @return the {@link CountryCode} parsed from the input String
     * @throws IllegalArgumentException if invalid mapping to enum occurrs.
     */
    public boolean isValidCountryCode(String barcode){
        CountryCode countryCode = CountryCode.valueOf(barcode.substring(barcode.length() - 2));
        if(!config.getValidCountryCodes().contains(countryCode)){
            throw new InvalidCountryCodeException("Country Code provided is not within list of valid values");
        }
        return true;
    }

    /**
     * Takes in the input barcode and returns
     * @param barcode input barcode String
     * @return left padding removed.
     */
    public Integer processBarcode(String barcode){
        String serialNumber = barcode.substring(2, barcode.length() - 3);
        Integer serialNumberInt = Integer.valueOf(serialNumber);
        if(!Pattern.compile(config.getSerialNumberRange()).matcher(serialNumberInt.toString()).matches()){
            throw new InvalidSerialNumberException("Invalid serial number : "+ serialNumber);
        }
        return serialNumberInt;
    }


    private boolean isValidPrefix(String barcode){
        Pattern prefixPatter = Pattern.compile(config.getPrefixValueRange());
        if(!prefixPatter.matcher(barcode.substring(0, 2)).matches()){
            throw new InvalidPrefixException("Invalid Prefix provided for barcode : "+barcode);
        }
        return true;
    }


}
