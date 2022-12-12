package com.basiliskSB.validation;
import java.lang.annotation.*;
import javax.validation.*;

@Constraint(validatedBy = AfterValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Afters.class)
public @interface After {

	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	public String message();
	public String previousDateField();
	public String subsequentDateField();
	
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	After[] value();
    }
}
