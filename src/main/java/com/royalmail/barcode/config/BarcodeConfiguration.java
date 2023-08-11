package com.royalmail.barcode.config;

import com.royalmail.barcode.entity.CountryCode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "royalmail.barcode")
public class BarcodeConfiguration {
    private String prefixValueRange;
    private String serialNumberRange;
    private List<CountryCode> validCountryCodes; // Do I need to add more logic to handle exceptions when mapping?
    private int validBarcodeLength;
    private String serialCodePattern;
    private List<Integer> s10Weightings;
}
