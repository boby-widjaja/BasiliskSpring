package com.basiliskSB.validation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.basiliskSB.service.abstraction.RegionService;

public class UniqueAssignRegionSalesmanValidator implements ConstraintValidator<UniqueAssignRegionSalesman, Object>{

    private String salesmanEmployeeNumberField;
    private String regionIdField;
	
	@Override
	public void initialize(UniqueAssignRegionSalesman constraintAnnotation) {
        this.salesmanEmployeeNumberField = constraintAnnotation.salesmanEmployeeNumberField();
        this.regionIdField = constraintAnnotation.regionIdField();
	}
    
	@Autowired
	private RegionService regionService;
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
        Long regionIdValue = (Long)(new BeanWrapperImpl(value).getPropertyValue(regionIdField));
        String salesmanEmployeeNumberValue = new BeanWrapperImpl(value).getPropertyValue(salesmanEmployeeNumberField).toString();
        return !regionService.checkExistingRegionSalesman(regionIdValue, salesmanEmployeeNumberValue);
	}

}
