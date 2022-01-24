package com.lloydsbank.api.atmservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lloydsbank.api.atmservice.TestUtil;
import com.lloydsbank.api.atmservice.model.AtmServiceResponse;
import com.lloydsbank.api.atmservice.model.LloydsAtmApiResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * The type Atm api service test.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AtmApiServiceTest {

    /**
     * The Atm api service.
     */
    @InjectMocks
    AtmApiService atmApiService;
    @Mock
    private RestTemplate restTemplate;

    /**
     * The Lloyds atm api response.
     */
    LloydsAtmApiResponse lloydsAtmApiResponse;
    /**
     * The Om.
     */
    ObjectMapper om;

    @BeforeAll
    private void init() throws IOException {
        MockitoAnnotations.openMocks(this);
        om = new ObjectMapper();
        TestUtil tu = new TestUtil();
        lloydsAtmApiResponse = om.readValue(tu.readFileFromResource("LloydsBankApiResponse.json"), LloydsAtmApiResponse.class);
        when(restTemplate.getForEntity("http://localhost:8080/test", LloydsAtmApiResponse.class)).thenReturn(new ResponseEntity<>(lloydsAtmApiResponse, HttpStatus.OK));
        when(restTemplate.getForEntity("http://localhost:8080/testexception", LloydsAtmApiResponse.class)).thenReturn(new ResponseEntity<>(lloydsAtmApiResponse, HttpStatus.NOT_FOUND));
    }

    /**
     * Test get atm service success.
     *
     * @throws IOException the io exception
     */
    @Test
    void testGetAtmServiceSuccess() {
        AtmServiceResponse atmServiceResponse = atmApiService.getAtmDetails("http://localhost:8080/test", "LFFFBC11");
        assertEquals(1, atmServiceResponse.getAtm().size(), "get ATM size should be 1");
        assertEquals("LFFFBC11", atmServiceResponse.getAtm().get(0).identification);
    }

    /**
     * Test get atm service success no record found.
     *
     * @throws IOException the io exception
     */
    @Test
    void testGetAtmServiceSuccess_NoRecordFound() {
        AtmServiceResponse atmServiceResponse = atmApiService.getAtmDetails("http://localhost:8080/test", "Test");
        assertEquals("Test", atmServiceResponse.identification);
        assertEquals("No Record Found", atmServiceResponse.message);
    }

}
