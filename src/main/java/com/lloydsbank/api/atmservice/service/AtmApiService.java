package com.lloydsbank.api.atmservice.service;

import com.lloydsbank.api.atmservice.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

/**
 * The type Atm api service.
 */
@Service
@Slf4j
public class AtmApiService {

    /**
     * The Rest template.
     */
    @Autowired
    RestTemplate restTemplate;

    /**
     * Gets atm details.
     *
     * @param url the url
     * @param id  the id
     * @return the atm details
     */
    public AtmServiceResponse getAtmDetails(String url, String id) {
        log.info("Invoking underlying API with request {} ",url);
        ResponseEntity<LloydsAtmApiResponse> apiRes = restTemplate.getForEntity(url, LloydsAtmApiResponse.class);
        List<Atm> atmList = new ArrayList<>();
        if (apiRes.getStatusCode().is2xxSuccessful()) {
            log.info("Response Code {} Received from underlying API",apiRes.getStatusCodeValue());
            log.debug("Response Received from Lloyd's Open Banking API {}",apiRes.getBody().toString());
            LloydsAtmApiResponse lloydsAtmApiResponse = apiRes.getBody();
            atmList = findAtm(Objects.requireNonNull(lloydsAtmApiResponse), id);
        } 
        if (!CollectionUtils.isEmpty(atmList)) {
            log.debug("Response Returned from API {}", atmList);
            return AtmServiceResponse.builder().atm(atmList).build();
        }
        else {
            log.info("No Record Found for identification {}",id);
            return AtmServiceResponse.builder().identification(id).msg("No Record Found").build();
        }
    }

    private List<Atm> findAtm(LloydsAtmApiResponse lloydsAtmApiResponse, String id) {
        List<AtmData> data = lloydsAtmApiResponse.getData();
        if (!CollectionUtils.isEmpty(data)) {
            return data.stream().filter(Objects::nonNull).map(atmData -> {
                List<Brand> brand = atmData.getBrand();
                if (!CollectionUtils.isEmpty(brand)) {
                    return brand.stream().filter(Objects::nonNull).map(brand1 -> getAtmWithId(id, brand1.getAtm())).findFirst().orElseGet(this::emptyAtmObj);
                } else return emptyAtmObj();
            }).findFirst().orElseGet(this::emptyAtmObj);
        } else return emptyAtmObj();

    }

    private List<Atm> getAtmWithId(String id, List<Atm> atm) {
        if (!CollectionUtils.isEmpty(atm)) {
            log.debug("List of Received ATMs - {}",atm);
            return atm.stream().collect(filtering(atm1 -> equalsIgnoreCase(atm1.getIdentification(), id), toList()));
        }
        return emptyAtmObj();
    }

    private List<Atm> emptyAtmObj() {
        return Collections.emptyList();
    }
}
