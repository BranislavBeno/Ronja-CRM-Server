package com.ronja.crm.ronjaserver.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class StatusValidator implements ConstraintValidator<Status, String> {

    private final List<String> statuses = List.of("ACTIVE", "INACTIVE");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return statuses.contains(value);
    }
}
