package com.oct.tools.wo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oct.tools.wo.repo.CurrencyRepo;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

	
	@Autowired
	private CurrencyRepo currencyRepo;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return currencyRepo.findByCode(value).isPresent();
	}
}
