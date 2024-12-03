package com.ronja.crm.ronjaserver.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String type) {
        super(String.format("%s neexistuje.", type));
    }

    public EntityNotFoundException(String type, String name) {
        super(String.format("%s '%s' neexistuje.", type, name));
    }
}
