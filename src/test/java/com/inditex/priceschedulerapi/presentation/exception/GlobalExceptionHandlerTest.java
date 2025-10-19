package com.inditex.priceschedulerapi.presentation.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleValidationExceptions_shouldReturnBadRequestAndProperErrorResponse() {
        // Arrange
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("TestObject", "field1", "Field1 is invalid");
        FieldError fieldError2 = new FieldError("TestObject", "field2", "Field2 is invalid");

        Mockito.when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError1, fieldError2));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleValidationExceptions(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GlobalExceptionHandler.ErrorResponse errorResponse = response.getBody();
        assertEquals(400, errorResponse.status());
        assertEquals("Validation failed", errorResponse.message());

        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("field1", "Field1 is invalid");
        expectedErrors.put("field2", "Field2 is invalid");

        assertEquals(expectedErrors, errorResponse.errors());
        LocalDateTime now = LocalDateTime.now();
        assertEquals(now.getYear(), errorResponse.timestamp().getYear());
        assertEquals(now.getMonth(), errorResponse.timestamp().getMonth());
        assertEquals(now.getDayOfMonth(), errorResponse.timestamp().getDayOfMonth());
    }

    @Test
    void handleConstraintViolationException_shouldReturnBadRequestAndProperErrorResponse() {
        // Arrange
        ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
        ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);
        Path path1 = mock(Path.class);
        Path path2 = mock(Path.class);

        when(path1.toString()).thenReturn("productId");
        when(path2.toString()).thenReturn("brandId");
        when(violation1.getPropertyPath()).thenReturn(path1);
        when(violation1.getMessage()).thenReturn("must be positive");
        when(violation2.getPropertyPath()).thenReturn(path2);
        when(violation2.getMessage()).thenReturn("cannot be null");

        ConstraintViolationException exception = new ConstraintViolationException(Set.of(violation1, violation2));

        // Act
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleConstraintViolationException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GlobalExceptionHandler.ErrorResponse errorResponse = response.getBody();
        assertEquals(400, errorResponse.status());
        assertEquals("Validation failed", errorResponse.message());
        assertEquals(2, errorResponse.errors().size());
        assertTrue(errorResponse.errors().containsKey("productId"));
        assertTrue(errorResponse.errors().containsKey("brandId"));
    }

    @Test
    void handleTypeMismatchException_shouldReturnBadRequestAndProperErrorResponse() {
        // Arrange
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "invalid-date",
                LocalDateTime.class,
                "applicationDate",
                mock(MethodParameter.class),
                new RuntimeException("Type mismatch")
        );

        // Act
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleTypeMismatchException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GlobalExceptionHandler.ErrorResponse errorResponse = response.getBody();
        assertEquals(400, errorResponse.status());
        assertEquals("Invalid parameter type", errorResponse.message());
        assertTrue(errorResponse.errors().containsKey("applicationDate"));
        assertEquals("Invalid value: invalid-date", errorResponse.errors().get("applicationDate"));
    }

    @Test
    void handleIllegalArgumentException_shouldReturnBadRequestAndProperErrorResponse() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Product ID must be positive");

        // Act
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        GlobalExceptionHandler.ErrorResponse errorResponse = response.getBody();
        assertEquals(400, errorResponse.status());
        assertEquals("Invalid argument", errorResponse.message());
        assertTrue(errorResponse.errors().containsKey("error"));
        assertEquals("Product ID must be positive", errorResponse.errors().get("error"));
    }

    @Test
    void handleGenericException_shouldReturnInternalServerErrorAndProperErrorResponse() {
        // Arrange
        Exception exception = new RuntimeException("Unexpected error");

        // Act
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        GlobalExceptionHandler.ErrorResponse errorResponse = response.getBody();
        assertEquals(500, errorResponse.status());
        assertEquals("Internal server error", errorResponse.message());
        assertTrue(errorResponse.errors().containsKey("error"));
        assertEquals("An unexpected error occurred", errorResponse.errors().get("error"));
    }
}