package com.basiliskSB.validation;
import java.lang.annotation.*;
import javax.validation.*;

@Constraint(validatedBy = UniqueCategoryNameValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCategoryName {

	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	public String message();
    public String idField();
    public String nameField();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	UniqueCategoryName[] value();
    }
}
