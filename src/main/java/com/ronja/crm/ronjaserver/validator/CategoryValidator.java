package com.ronja.crm.ronjaserver.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CategoryValidator implements ConstraintValidator<Category, String> {

    private final List<String> categories = List.of("LEVEL_1", "LEVEL_2", "LEVEL_3");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return categories.contains(value);
    }
}
