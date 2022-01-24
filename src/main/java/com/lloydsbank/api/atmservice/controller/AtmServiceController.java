package com.lloydsbank.api.atmservice.controller;

import com.lloydsbank.api.atmservice.model.AtmServiceResponse;
import com.lloydsbank.api.atmservice.service.AtmApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The type Atm service controller.
 */
@RestController
@Slf4j
@RequestMapping("/api/v1")
@Validated
@Tag(name = "ATM Service API")
public class AtmServiceController {

    /**
     * The Api service.
     */
    @Autowired
    AtmApiService apiService;

    @Value("${apihostnames}")
    private String validHosts;

    /**
     * Gets atms.
     *
     * @param url            the url
     * @param identification the id
     * @return the atms
     */

    @Operation(summary = "Get ATMs based on the Identification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ATM Details found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AtmServiceResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content ),
            @ApiResponse(responseCode = "404", description = "Resource Not Found ", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error Occurred", content = @Content)
    })

    @GetMapping(value = "/getatms", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    AtmServiceResponse getAtms(@Parameter(description = "Lloyd's Open Banking Atm Url",example = "https://api.lloydsbank.com/open-banking/v2.2/atms") @Valid @NotBlank @RequestParam("url") String url, @Parameter(description = "Unique ATM identification",example = "LFFFBC11") @Valid @NotBlank @Size(max = 35) @RequestParam("identification") String identification) {
        log.info("Request Received with URL {} and Identification {}", url, identification);
        validateUrl(url);
        return apiService.getAtmDetails(url, identification);
    }

    private void validateUrl(String input) {
        URL url;
        try {
            url = new URI(input).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Url");
        }
        if (!url.getHost().matches(validHosts))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Hostname " + url.getHost());
    }
}
