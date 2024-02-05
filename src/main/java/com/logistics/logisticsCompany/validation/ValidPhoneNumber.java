package com.logistics.logisticsCompany.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface ValidPhoneNumber {
	String message() default "Invalid phone number. It must start with 08 and have 10 digits in total.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
