package com.basiliskSB.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.basiliskSB.service.abstraction.AccountService;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>{
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return !accountService.checkExistingAccount(username);
	}

}
