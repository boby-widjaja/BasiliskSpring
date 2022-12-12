package com.basiliskSB.validation;
import java.lang.annotation.*;
import javax.validation.*;

@Constraint(validatedBy = UniqueSalesmanNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueSalesmanNumber {
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	public String message();
}
