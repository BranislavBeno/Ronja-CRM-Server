package com.ronja.crm.ronjaserver.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ContactTypeValidator implements ConstraintValidator<ContactType, String> {

    private final List<String> categories = List.of("PERSONAL", "MAIL", "PHONE", "ON_LINE");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return categories.contains(value);
    }
}
