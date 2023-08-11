package com.royalmail.barcode.controller;

import com.royalmail.barcode.utilities.BarcodeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarcodeValidationController {

    @Autowired
    BarcodeValidator validator;

    @GetMapping("/validate/{barcode}")
    public ResponseEntity<Boolean> validateBarcode(@PathVariable(required = true) String barcode) {

        boolean isValid = validator.validateBarcode(barcode);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
}
