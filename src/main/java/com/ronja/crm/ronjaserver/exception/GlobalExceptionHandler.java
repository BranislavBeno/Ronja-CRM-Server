package com.ronja.crm.ronjaserver.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  ObjectMapper mapper;

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> inputValidationException(ConstraintViolationException e) throws JsonProcessingException {
    String message = String.format("Invalid request: %s", e.getMessage());

    if (e.getConstraintViolations() != null) {
      List<Violation> violations = e.getConstraintViolations().stream()
          .map(v -> new Violation(v.getPropertyPath().toString(), v.getMessage())).toList();
      ValidationErrorResponse error = new ValidationErrorResponse(violations);
      message = mapper.writeValueAsString(error);
    }

    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  private record Violation(String fieldName, String message) {
  }

  private record ValidationErrorResponse(Collection<Violation> violations) {
  }
}
