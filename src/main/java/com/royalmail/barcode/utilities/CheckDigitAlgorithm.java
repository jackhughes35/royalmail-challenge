package com.royalmail.barcode.utilities;

import org.springframework.stereotype.Component;

@Component
public interface CheckDigitAlgorithm {

    boolean isValidCheckDigit(Integer inputSerialCode, int inputCheckDigit);
}
