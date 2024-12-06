package com.ronja.crm.ronjaserver.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String type) {
        super("%s neexistuje.".formatted(type));
    }

    public EntityNotFoundException(String type, String name) {
        super("%s '%s' neexistuje.".formatted(type, name));
    }
}
