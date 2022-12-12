package com.basiliskSB.validation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.basiliskSB.service.abstraction.DeliveryService;

public class UniqueDeliveryCompanyValidator implements ConstraintValidator<UniqueDeliveryCompany, Object> {

    private String idField;
    private String deliveryCompanyField;
	
	@Override
	public void initialize(UniqueDeliveryCompany constraintAnnotation) {
        this.idField = constraintAnnotation.idField();
        this.deliveryCompanyField = constraintAnnotation.deliveryCompanyField();
	}
	
	@Autowired
	private DeliveryService deliveryService;
    
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
        Long idValue = (Long)(new BeanWrapperImpl(value).getPropertyValue(idField));
        String deliveryCompanyValue = new BeanWrapperImpl(value).getPropertyValue(deliveryCompanyField).toString(); 
		return !deliveryService.checkExistingDeliveryName(idValue, deliveryCompanyValue);
	}

}
