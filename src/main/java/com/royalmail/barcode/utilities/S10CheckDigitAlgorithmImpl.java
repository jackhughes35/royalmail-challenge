package com.royalmail.barcode.utilities;

public class S10CheckDigitAlgorithmImpl implements CheckDigitAlgorithm {
    @Override
    public boolean isValidCheckDigit() {
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
}
