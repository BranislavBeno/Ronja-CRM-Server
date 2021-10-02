package com.ronja.crm.ronjaserver.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FocusValidator implements ConstraintValidator<Focus, String> {

  final List<String> focuses = List.of("BUILDER", "MANUFACTURE", "SPECIALIZED_MANUFACTURE", "TRADE");

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return focuses.contains(value);
  }
}
