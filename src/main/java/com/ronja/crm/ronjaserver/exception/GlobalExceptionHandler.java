package com.ronja.crm.ronjaserver.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tools.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper mapper;

    public GlobalExceptionHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> inputValidationException(ConstraintViolationException e) {
        String message = "Invalid request: %s".formatted(e.getMessage());

        if (e.getConstraintViolations() != null) {
            List<Violation> violations = e.getConstraintViolations().stream()
                    .map(v -> new Violation(v.getPropertyPath().toString(), v.getMessage())).toList();
            ValidationErrorResponse error = new ValidationErrorResponse(violations);
            message = mapper.writeValueAsString(error);
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundException(EntityNotFoundException e) {
        List<Violation> violations = List.of(new Violation("Whole entity", e.getMessage()));
        ValidationErrorResponse error = new ValidationErrorResponse(violations);
        String message = mapper.writeValueAsString(error);

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    private record Violation(String fieldName, String message) {
    }

    private record ValidationErrorResponse(Collection<Violation> violations) {
    }
}
