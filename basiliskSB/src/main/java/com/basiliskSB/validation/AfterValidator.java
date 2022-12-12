package com.basiliskSB.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.*;
import org.springframework.beans.BeanWrapperImpl;

public class AfterValidator implements ConstraintValidator<After, Object> {

	private String previousDateField;
	private String subsequentDateField;
	
	@Override
	public void initialize(After constraintAnnotation) {
        this.previousDateField = constraintAnnotation.previousDateField();
        this.subsequentDateField = constraintAnnotation.subsequentDateField();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDate previousDateValue = (LocalDate)(new BeanWrapperImpl(value).getPropertyValue(previousDateField));
        LocalDate subsequentDateValue = (LocalDate)(new BeanWrapperImpl(value).getPropertyValue(subsequentDateField));
        if(previousDateValue == null || subsequentDateValue == null) {
        	return true;
        }
        return previousDateValue.isBefore(subsequentDateValue);
	}
}
