package com.basiliskSB.validation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.basiliskSB.service.abstraction.SalesmanService;

public class UniqueSalesmanNumberValidator implements ConstraintValidator<UniqueSalesmanNumber, String>  {

	@Autowired
	private SalesmanService salesmanService;
	
	@Override
	public boolean isValid(String employeeNumber, ConstraintValidatorContext context) {
		return !salesmanService.checkExistingSalesman(employeeNumber);
	}

}
