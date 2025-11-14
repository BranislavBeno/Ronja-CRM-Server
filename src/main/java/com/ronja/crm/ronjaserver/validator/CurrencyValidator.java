package com.ronja.crm.ronjaserver.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class CurrencyValidator implements ConstraintValidator<Currency, String> {

  private final List<String> currencies = List.of("USD", "EUR");

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return currencies.contains(value);
  }
}
