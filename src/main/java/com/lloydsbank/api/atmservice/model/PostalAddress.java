package com.lloydsbank.api.atmservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostalAddress {
	@JsonProperty("AddressLine")
	public List<String> addressLine;
	@JsonProperty("StreetName")
	public String streetName;
	@JsonProperty("TownName")
	public String townName;
	@JsonProperty("CountrySubDivision")
	public List<String> countrySubDivision;
	@JsonProperty("Country")
	public String country;
	@JsonProperty("PostCode")
	public String postCode;


}
