package com.oct.tools.wo.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oct.tools.wo.validator.ConditionalWO;
import com.oct.tools.wo.validator.ValidCurrency;
import com.oct.tools.wo.validator.ValidDateRange;
import com.oct.tools.wo.validator.ValidDepartment;

import lombok.Data;

@Data
@ValidDateRange(message = "{date.range.invalid}")
@ConditionalWO(selected = "type", values = {"REPAIR", "REPLACEMENT"}, 
required = {"analysisDate","responsiblePerson","testDate","parts","factoryName","factoryOrderNumber"})
public class WorkOrderDto {

	private Long logId;
	
	private String type;
	
	@JsonProperty("department")
	@ValidDepartment(message = 	"{department.invalid}")
	private String departmentName;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("start_date")
	private LocalDate startDate;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("end_date")
	private LocalDate endDate;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("analysis_date")
	private LocalDate analysisDate;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("test_date")
	private LocalDate testDate;
	
	@JsonProperty("responsible_person")
	private String responsiblePerson;
	
	@JsonProperty("factory_name")
	private String factoryName;
	
	@JsonProperty("factory_order_number")
	private String factoryOrderNumber;
	
	@ValidCurrency(message = "{currency.invalid}")
	private String currency;
	
	@Min(value =1, message = "{cost.invalid}")
	private Double cost;
	
	private List<WOPartDto> parts;
	
}
