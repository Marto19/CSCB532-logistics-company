package com.logistics.logisticsCompany.customExceptions;

/**
 * The EntityNotFoundException class is a custom exception that is thrown when an entity is not found in the system.
 */
public class EntityNotFoundException  extends  RuntimeException{
    /**
     * Constructs an EntityNotFoundException with the specified message.
     * @param message the message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
