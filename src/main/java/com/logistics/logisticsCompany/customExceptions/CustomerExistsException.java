package com.logistics.logisticsCompany.customExceptions;

public class CustomerExistsException extends RuntimeException {
	public CustomerExistsException(String message) {
		super(message);
	}
}
