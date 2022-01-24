package com.lloydsbank.api.atmservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtmServiceResponse extends BaseResponse{
	@JsonProperty("ATM")
	public List<Atm> atm;

	@Builder
	AtmServiceResponse(List<Atm> atm,String identification,String msg){
		super(identification,msg);
		this.identification = identification;
		this.message= msg;
		this.atm = atm;
	}


}
