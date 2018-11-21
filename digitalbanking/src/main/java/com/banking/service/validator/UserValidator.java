package com.banking.service.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.banking.entity.User;

public class UserValidator implements Validator {

	private final Validator userValidator;

	public UserValidator(Validator userValidator) {
		if (userValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is " + "required and must not be null.");
		}
		this.userValidator = userValidator;
	}

	/**
	 * This Validator validates User instances, and any subclasses of User
	 * too
	 */
	public boolean supports(Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required");
		
		User customer = (User) target;
		
		try {
			errors.pushNestedPath("email");
			ValidationUtils.invokeValidator(this.userValidator, customer.getEmail(), errors);
		} finally {
			errors.popNestedPath();
		}
	}
}