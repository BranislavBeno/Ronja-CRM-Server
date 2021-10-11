package com.ronja.crm.ronjaserver.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CategoryValidator.class)
@Documented
public @interface Category {

  String message() default "Neznáma kategória.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
