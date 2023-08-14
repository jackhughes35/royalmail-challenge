package com.royalmail.barcode.utilities;

import com.royalmail.barcode.config.BarcodeConfiguration;
import com.royalmail.barcode.exception.InvalidSerialNumberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class S10CheckDigitAlgorithmImpl implements CheckDigitAlgorithm {
    @Autowired
    BarcodeConfiguration config;

    @Override
    public boolean isValidCheckDigit(Integer inputSerialCode, int inputCheckDigit) {
        // take the input serial code to an array
        String inputAsString = inputSerialCode.toString();
        int[] inputs = inputAsString.chars().map(Character::getNumericValue).toArray();

        // Assign the weights 8, 6, 4, 2, 3, 5, 9, 7 to the 8 digits, from left to right
        // Calculate the sum, of each digit multiplied by its weight.
        int runningTotal = 0;
        for(int input= 0; input < inputs.length; input++){
            runningTotal += (config.getS10Weightings().get(input) * inputs[input]);
        }

        int remainder = runningTotal % 11;
        if (remainder == 1){
            remainder = 0;
        } else if (remainder == 0) {
            remainder = 5;
        } else {
            remainder = 11 - remainder;
        }

        return (remainder == inputCheckDigit);

    }
}
