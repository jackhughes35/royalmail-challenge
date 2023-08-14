package com.royalmail.barcode.integrationtests;

import com.royalmail.barcode.config.BarcodeConfiguration;
import com.royalmail.barcode.controller.BarcodeValidationController;
import com.royalmail.barcode.utilities.BarcodeValidator;
import com.royalmail.barcode.utilities.S10CheckDigitAlgorithmImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BarcodeConfiguration.class })
@WebAppConfiguration
@TestPropertySource("classpath:application.properties")
public class IntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final String correctUri = "/validate";
    private String trackingHeader = "trackingHeader";

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    @ParameterizedTest
    @MethodSource("provideStringsForTest")
    public void test(String pathParam, boolean expectedResponse) throws Exception {
        StringBuilder sb = new StringBuilder(correctUri).append("/").append(pathParam);

        // Carry out request
        MvcResult result = mockMvc.perform(buildRequest(buildUri(sb.toString()), trackingHeader))
                .andReturn();

        // Assertions taken from input stream
        Assertions.assertEquals(expectedResponse, Boolean.valueOf(result.getResponse().getContentAsString()));
        Assertions.assertEquals(HttpStatus.OK, result.getResponse().getStatus());
    }

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
    private static Stream<Arguments> provideStringsForTest() {
        // Valid
        String validInput = "AA473124829GB";
        String validInput_withZeroPadding = "AA00473124829GB";
        // Invalid
        String invalidInput_invalidCheckDigit = "AA473124828GB";
        String invalidInput_invalidLength = "AA47312482GB";
        String invalidInput_invalidPrefix = "A3473124829GB";
        String invalidInput_codeContainsLetters = "SAA4A3124829GB";
        String invalidInput_codeContainsSymbols = "AA47!124829GB";
        String invalidInput_countryCodeInvalid = "AA47!124829NZ";

        return Stream.of(
                Arguments.of(validInput, true),
                Arguments.of(validInput_withZeroPadding, true),
                Arguments.of(invalidInput_invalidCheckDigit, false),
                Arguments.of(invalidInput_invalidPrefix, false),
                Arguments.of(invalidInput_codeContainsLetters, false),
                Arguments.of(invalidInput_codeContainsSymbols, false),
                Arguments.of(invalidInput_countryCodeInvalid, false)
        );
    }
}
