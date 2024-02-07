package com.logistics.logisticsCompany.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator class for validating phone numbers.
 */
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
	
	private static final String PHONE_NUMBER_PATTERN = "^08[0-9]{8}$";

	/**
	 * Initializes the validator. This method is empty as no initialization is needed for this validator.
	 *
	 * @param constraintAnnotation The annotation instance for a given element.
	 */
	@Override
	public void initialize(ValidPhoneNumber constraintAnnotation) {
	}

	/**
	 * Checks if a phone number is valid.
	 *
	 * @param phoneNumber The phone number to validate.
	 * @param context Context in which the constraint is evaluated.
	 * @return true if the phone number is valid, false otherwise.
	 */
	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
		if (phoneNumber == null) {
			return false;
		}
		return phoneNumber.matches(PHONE_NUMBER_PATTERN);
	}
}
