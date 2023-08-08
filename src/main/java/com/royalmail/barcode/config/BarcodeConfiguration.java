package com.royalmail.barcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "royalmail.barcode")
public class BarcodeConfiguration {
    private String prefixRange;
    private String serialNumberRange;
    private String validCountryCode;
    private int validBarcodeLength;

    private String serialCodePattern;
}
