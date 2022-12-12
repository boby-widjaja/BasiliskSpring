package com.basiliskSB.validation;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FutureParadoxValidator implements ConstraintValidator<FutureParadox, LocalDate>  {
	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if(value == null) {
			return true;
		}
		return value.isBefore(LocalDate.now());
	}

}
