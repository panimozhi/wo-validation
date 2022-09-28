package com.oct.tools.wo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.oct.tools.wo.repo.DepartmentRepo;

public class DepartmentValidator implements ConstraintValidator<ValidDepartment, String> {

	
	@Autowired
	private DepartmentRepo departmentRepo;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.hasText(value) && departmentRepo.findByDepartmentName(value).isPresent();
	}
}
