package com.oct.tools.wo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Currency {

	
	@Id
	private String code;
	private String name;
	private String symbol;
}
