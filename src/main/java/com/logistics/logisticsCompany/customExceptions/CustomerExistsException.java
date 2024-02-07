package com.logistics.logisticsCompany.customExceptions;

/**
 * The CustomerExistsException class is a custom exception that is thrown when a customer already exists in the system.
 */
public class CustomerExistsException extends RuntimeException {
	/**
	 * Constructs a CustomerExistsException with the specified message.
	 * @param message the message
	 */
	public CustomerExistsException(String message) {
		super(message);
	}
}
