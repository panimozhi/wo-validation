package com.oct.tools.wo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WOPartDto {

	@JsonProperty("inventory_number")
	private String inventoryNumber;
	
	@JsonProperty("name")
	private String partName;
	
	@JsonProperty("count")
	private Integer count;
}
