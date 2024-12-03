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
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper mapper;

    public GlobalExceptionHandler(@Autowired ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> inputValidationException(ConstraintViolationException e) throws JsonProcessingException {
        String message = String.format("Invalid request: %s", e.getMessage());

        if (e.getConstraintViolations() != null) {
            List<Violation> violations = e.getConstraintViolations().stream()
                    .map(v -> new Violation(v.getPropertyPath().toString(), v.getMessage())).collect(Collectors.toUnmodifiableList());
            ValidationErrorResponse error = new ValidationErrorResponse(violations);
            message = mapper.writeValueAsString(error);
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundException(EntityNotFoundException e) throws JsonProcessingException {
        List<Violation> violations = List.of(new Violation("Whole entity", e.getMessage()));
        ValidationErrorResponse error = new ValidationErrorResponse(violations);
        String message = mapper.writeValueAsString(error);

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    private static final class Violation {

        private final String fieldName;
        private final String message;

        private Violation(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getMessage() {
            return message;
        }
    }

    private static final class ValidationErrorResponse {

        private final Collection<Violation> violations;

        private ValidationErrorResponse(Collection<Violation> violations) {
            this.violations = violations;
        }

        public Collection<Violation> getViolations() {
            return violations;
        }
    }
}
