package com.lloydsbank.api.atmservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
	@JsonProperty("PostalAddress")
	public PostalAddress postalAddress;

}
