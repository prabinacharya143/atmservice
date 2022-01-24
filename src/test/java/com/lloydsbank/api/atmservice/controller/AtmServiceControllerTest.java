package com.lloydsbank.api.atmservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lloydsbank.api.atmservice.TestUtil;
import com.lloydsbank.api.atmservice.model.AtmServiceResponse;
import com.lloydsbank.api.atmservice.service.AtmApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Atm service controller test.
 */
@WebMvcTest(AtmServiceController.class)
class AtmServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AtmApiService atmApiService;

    @MockBean
    private RestTemplate restTemplate;

    /**
     * Success response.
     *
     * @throws Exception the exception
     */
    @Test
    void successResponse() throws Exception {
        ObjectMapper om = new ObjectMapper();
        TestUtil tu = new TestUtil();
        AtmServiceResponse atmServiceResponse = om.readValue(tu.readFileFromResource("AtmServiceSuccessResponse.json"), AtmServiceResponse.class);
        System.out.println(atmServiceResponse.toString());
        when(atmApiService.getAtmDetails(anyString(), anyString())).thenReturn(atmServiceResponse);
        this.mockMvc
                .perform(get("/api/v1/getatms")
                        .param("url", "http://localhost:8080/openbankatmapi")
                        .param("identification", "testid"))
                .andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.ATM").exists())
                .andExpect(jsonPath("$.ATM").isArray())
                .andExpect(jsonPath("$.ATM[0].Identification").value("LFFFBC11"))
                .andExpect(jsonPath("$.ATM[0].Location").exists());

        verify(atmApiService).getAtmDetails(anyString(),anyString());
    }

    /**
     * Invalid url test.
     *
     * @throws Exception the exception
     */
    @Test
    void invalidUrlTest() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/getatms")
                            .param("url", "http://invalidlocalhost:8080/openbankatmapi")
                            .param("identification", "testid"))
                .andExpect(status().is4xxClientError());
    }




}
