package com.logistics.logisticsCompany.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
	
	private static final String PHONE_NUMBER_PATTERN = "^08[0-9]{8}$";
	
	@Override
	public void initialize(ValidPhoneNumber constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
		if (phoneNumber == null) {
			return false;
		}
		return phoneNumber.matches(PHONE_NUMBER_PATTERN);
	}
}
