package com.oct.tools.wo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.util.StringUtils;

import com.oct.tools.wo.dto.WorkOrderDto;
import com.oct.tools.wo.entity.WOType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConditionalWOValidator implements ConstraintValidator<ConditionalWO, WorkOrderDto> {

	private String message;
	private String[] required;

	@Override
	public void initialize(ConditionalWO requiredIfChecked) {
		message = requiredIfChecked.message();
		required = requiredIfChecked.required();
	}

	@Override
	public boolean isValid(WorkOrderDto value, ConstraintValidatorContext context) {
		Boolean errorFound = false;
		try {
			context.disableDefaultConstraintViolation();
			for (String propName : required) {
				Boolean valid = true;
				if (String.valueOf(WOType.REPAIR).equalsIgnoreCase(value.getType())) {
					switch (propName) {
					case "analysisDate":
						valid = (value.getStartDate() != null && value.getEndDate() != null
								&& value.getAnalysisDate() != null
								&& value.getAnalysisDate().isAfter(value.getStartDate())
								&& value.getAnalysisDate().isBefore(value.getEndDate()));
						message = "{analysis.date.error}";
						break;
					case "responsiblePerson":
						valid = StringUtils.hasText(value.getResponsiblePerson());
						message = "{responsible.person.error}";
						break;
					case "testDate":
						valid = (value.getTestDate() != null && value.getEndDate() != null
								&& value.getAnalysisDate() != null
								&& value.getTestDate().isAfter(value.getAnalysisDate())
								&& value.getTestDate().isBefore(value.getEndDate()));
						message = "{test.date.error}";
						break;
					case "parts":
						valid = value.getParts() != null
								&& value.getParts().stream().mapToInt(s -> s.getCount()).sum() > 0;
						message = "{parts.error}";
						break;
					default:
						break;
					}
				} else if (String.valueOf(WOType.REPLACEMENT).equalsIgnoreCase(value.getType())) {
					switch (propName) {
					case "factoryName":
						valid = (value.getFactoryName() != null);
						message = "{factory.error}";
						break;
					case "factoryOrderNumber":
						valid = value.getFactoryOrderNumber() != null
								&& value.getFactoryOrderNumber().matches("^[a-zA-Z][a-zA-Z][0-9]{8}$");
						message = "{factory.order.error}";
						break;
					case "parts":
						valid = value.getParts() != null && value.getParts().stream()
								.filter(p -> !StringUtils.hasText(p.getInventoryNumber())).findAny().isPresent();
						message = "{parts.error}";
						break;
					default:
						break;
					}
				}

				if (!valid) {
					errorFound = true;
					HibernateConstraintValidatorContext ctx = context.unwrap(HibernateConstraintValidatorContext.class);
					ctx.addMessageParameter(propName, BeanUtils.getProperty(value, propName));
					ctx.buildConstraintViolationWithTemplate(message).addConstraintViolation();
				}
			}
			
			if(errorFound)
				return false;
			
		} catch (Exception e) {
			log.error("Error in finding the property in the bean", e);
		}
		return true;
	}
}