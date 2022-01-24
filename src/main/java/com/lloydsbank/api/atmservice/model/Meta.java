package com.lloydsbank.api.atmservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
    @JsonProperty("Agreement")
    private String agreement;
    @JsonProperty("LastUpdated")
    private String lastUpdated;
    @JsonProperty("License")
    private String license;
    @JsonProperty("TermsOfUse")
    private String termsOfUse;
    @JsonProperty("TotalResults")
    private Long totalResults;
}