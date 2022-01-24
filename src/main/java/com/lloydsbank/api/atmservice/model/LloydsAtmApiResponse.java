package com.lloydsbank.api.atmservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LloydsAtmApiResponse {
	@JsonProperty("data")
	private List<AtmData> data;

	@JsonProperty("meta")
	private Meta meta;
}
