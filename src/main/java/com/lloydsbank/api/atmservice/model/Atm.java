package com.lloydsbank.api.atmservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atm {
	@JsonProperty("Identification")
	public String identification;
	@JsonProperty("SupportedCurrencies")
	public List<String> supportedCurrencies;
	@JsonProperty("Location")
	public Location location;
}
