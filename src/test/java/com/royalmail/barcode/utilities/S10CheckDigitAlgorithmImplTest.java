package com.royalmail.barcode.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class S10CheckDigitAlgorithmImplTest {
    @Autowired
    S10CheckDigitAlgorithmImpl algorithm;

    private String inputValidBarcode = "47312482";
    private String inputValidBarcode2 = "10002799";
    private String inputValidBarcode3 = "12345678";
    private String inputInvalidBarcode = "47312482";

    @Test
    public void isValidCheckDigit_SuccessScenario(){
        Assertions.assertTrue(algorithm.isValidCheckDigit(Integer.valueOf(inputValidBarcode), 9));
    }

    @Test
    public void isValidCheckDigit_SuccessScenario2(){
        Assertions.assertTrue(algorithm.isValidCheckDigit(Integer.valueOf(inputValidBarcode2), 5));
    }

    @Test
    public void isValidCheckDigit_SuccessScenario3(){
        Assertions.assertTrue(algorithm.isValidCheckDigit(Integer.valueOf(inputValidBarcode3), 5));
    }

    @Test
    public void isValidCheckDigit_FailureScenario(){
        Assertions.assertFalse(algorithm.isValidCheckDigit(Integer.valueOf(inputValidBarcode), 5));
    }

}
