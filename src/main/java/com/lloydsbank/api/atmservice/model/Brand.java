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
public class Brand {
	@JsonProperty("BrandName")
	public String brandName;
	@JsonProperty("ATM")
	public List<Atm> atm;

}
