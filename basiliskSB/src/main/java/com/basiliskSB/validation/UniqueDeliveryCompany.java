package com.basiliskSB.validation;
import java.lang.annotation.*;
import javax.validation.*;

@Constraint(validatedBy = UniqueDeliveryCompanyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDeliveryCompany {

	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	public String message();
    public String idField();
    public String deliveryCompanyField();	
	
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	UniqueCategoryName[] value();
    }
}
