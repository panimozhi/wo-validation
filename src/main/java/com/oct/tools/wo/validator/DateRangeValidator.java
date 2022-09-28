package com.oct.tools.wo.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.oct.tools.wo.dto.WorkOrderDto;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, WorkOrderDto> {

	@Override
	public boolean isValid(WorkOrderDto value, ConstraintValidatorContext context) {
		boolean valid = value.getStartDate() != null &&
				value.getEndDate() != null &&
				value.getStartDate().isAfter(LocalDate.now())
				&& value.getEndDate().isAfter(value.getStartDate());
		return valid;
	}


}
