package com.logistics.logisticsCompany.customExceptions;

public class EntityNotFoundException  extends  RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
