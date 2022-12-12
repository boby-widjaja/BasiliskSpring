package com.basiliskSB.validation;
import javax.validation.*;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.basiliskSB.service.abstraction.CategoryService;

public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, Object> {

    private String idField;
    private String nameField;
	
	@Override
	public void initialize(UniqueCategoryName constraintAnnotation) {
        this.idField = constraintAnnotation.idField();
        this.nameField = constraintAnnotation.nameField();
	}
    
	@Autowired
	private CategoryService categoryService;	
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
        Long idValue = (Long)(new BeanWrapperImpl(value).getPropertyValue(idField));
        String nameValue = new BeanWrapperImpl(value).getPropertyValue(nameField).toString();
		return !categoryService.checkExistingCategoryName(idValue, nameValue);
	}

}
