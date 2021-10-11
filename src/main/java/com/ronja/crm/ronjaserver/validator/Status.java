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
@Constraint(validatedBy = StatusValidator.class)
@Documented
public @interface Status {

  String message() default "Neznámy stav.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
