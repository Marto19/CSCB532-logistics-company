package com.logistics.logisticsCompany.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Annotation for validating phone numbers.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface ValidPhoneNumber {

	/**
	 * Default error message.
	 *
	 * @return The error message.
	 */
	String message() default "Invalid phone number. It must start with 08 and have 10 digits in total.";

	/**
	 * Groups can be used to control the order of validation.
	 *
	 * @return The groups.
	 */
	Class<?>[] groups() default {};

	/**
	 * Can be used by clients of the Bean Validation API to assign custom payload objects to a constraint.
	 * This attribute is not used by the Bean Validation API itself.
	 *
	 * @return The payload.
	 */
	Class<? extends Payload>[] payload() default {};
}
