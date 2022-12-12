package com.basiliskSB.validation;
import java.lang.annotation.*;
import javax.validation.*;

@Constraint(validatedBy = UniqueAssignRegionSalesmanValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueAssignRegionSalesman {

	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	public String message();
    public String salesmanEmployeeNumberField();
    public String regionIdField();
	
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	UniqueCategoryName[] value();
    }
}
