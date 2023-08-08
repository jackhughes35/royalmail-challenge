package com.royalmail.barcode.controller;

import com.royalmail.barcode.utilities.BarcodeValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BarcodeValidationController.class)
@TestPropertySource("classpath:application.properties")
public class BarcodeValidationControllerTest {

    private final String correctUri = "/validate";
    private String incorrectUri = "";
    private String trackingHeader = "trackingHeader";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BarcodeValidator validator;

    /**
     * Testing scenario where incorrect endpoint is hit
     * Expected: HTTP 404
     */
    @Test
    public void testvalidateBarcode_FailureScenario_IncorrectEndpoint() throws Exception {
        // Declare Variables
        String pathParam = "value";
        StringBuilder sb = new StringBuilder(incorrectUri).append("/").append(pathParam);

        // Carry out request
        MvcResult result = mockMvc.perform(buildRequest(buildUri(sb.toString()), trackingHeader))
                .andReturn();

        // Assertions
        Assertions.assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus())
        );
    }

    /**
     * Testing scenario where endpoint is hit with incorrect HTTP method
     * Expected: HTTP 405
     */
    @Test
    public void testvalidateBarcode_FailureScenario_IncorrectMethod() throws Exception {
        // Declare Variables
        String pathParam = "value";
        StringBuilder sb = new StringBuilder(correctUri).append("/").append(pathParam);

        // Build Request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(buildUri(sb.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .header("E2E-Tracking-Header", trackingHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8");

        // Carry out request
        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();
        // Assertions
        Assertions.assertAll(
                () -> assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), result.getResponse().getStatus())
        );
    }

    /**
     * Testing scenario where mandatory path parameter is not present
     * Expected: HTTP 404
     */
    @Test
    public void testvalidateBarcode_FailureScenario_missingBarcode() throws Exception {
        // Declare Variables
        String str = new String(); // null passed
        StringBuilder sb = new StringBuilder(correctUri).append("/").append(str);

        // Carry out request
        MvcResult result = mockMvc.perform(buildRequest(buildUri(sb.toString()), trackingHeader))
                .andReturn();

        // Assertions
        Assertions.assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus())
        );
    }

    /**
     * Testing scenario where request parameter is empty
     * Expected: HTTP 200, boolean False
     */
    @Test
    public void testvalidateBarcode_FailureScenario_emptyParameter() throws Exception {
        // Declare Variables
        String pathParam = " ";
        StringBuilder sb = new StringBuilder(correctUri).append("/").append(pathParam);

        // Mocks
        Mockito.when(validator.validateBarcode(Mockito.eq(pathParam))).thenReturn(false);

        // Carry out request
        MvcResult result = mockMvc.perform(buildRequest(buildUri(sb.toString()), trackingHeader))
                .andReturn();

        // Assertions
        Assertions.assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(Boolean.FALSE, Boolean.valueOf(result.getResponse().getContentAsString()))
        );
    }

    /**
     * Testing scenario where validator returns true
     * Expected: HTTP 200, boolean true
     */
    @Test
    public void testvalidateBarcode_SuccessScenario() throws Exception {
        // Declare Variables
        String pathParam = "validInput";
        StringBuilder sb = new StringBuilder(correctUri).append("/").append(pathParam);

        // Mocks
        Mockito.when(validator.validateBarcode(Mockito.eq(pathParam))).thenReturn(true);

        // Carry out request
        MvcResult result = mockMvc.perform(buildRequest(buildUri(sb.toString()), trackingHeader))
                .andReturn();

        // Assertions
        Assertions.assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(Boolean.TRUE, Boolean.valueOf(result.getResponse().getContentAsString()))
        );
    }

    /**
     * Testing scenario where validator throws {@link com.royalmail.barcode.exception.BarcodeValidationException}
     * Expected: HTTP 400
     */

    /**
     * Testing scenario where validator throws {@link com.royalmail.barcode.exception.InvalidCountryCodeException}
     * Expected: HTTP 400
     */

    /**
     * Testing scenario where validator throws {@link com.royalmail.barcode.exception.InvalidCheckDigitException}
     * Expected: HTTP 400
     */

    /**
     * Testing scenario where validator throws {@link com.royalmail.barcode.exception.InvalidPrefixException}
     * Expected: HTTP 400
     */

    /**
     * Testing scenario where validator throws {@link com.royalmail.barcode.exception.InvalidSerialNumberException}
     * Expected: HTTP 400
     */

    private RequestBuilder buildRequest(String uri, String trackingHeader){
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header("E2E-Tracking-Header", trackingHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8");
        return requestBuilder;

    }

    /**
     * Helper method to create URI from a given path.
     * @param path the desired path
     * @return String uri
     */
    private String buildUri(String path){
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path(path)
                .build().toUriString();
    }
}
