package com.basiliskSB.validation;
import javax.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.basiliskSB.service.abstraction.OrderService;

public class UniqueInvoiceNumberValidator implements ConstraintValidator<UniqueInvoiceNumber, String> {

	@Autowired
	private OrderService orderService;
	
	@Override
	public boolean isValid(String invoiceNumber, ConstraintValidatorContext context) {
		return !orderService.checkExistingOrder(invoiceNumber);
	}

}
