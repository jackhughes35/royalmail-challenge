package com.royalmail.barcode.utilities;

public interface CheckDigitAlgorithm {

    boolean isValidCheckDigit(Integer inputSerialCode, int inputCheckDigit);
}
