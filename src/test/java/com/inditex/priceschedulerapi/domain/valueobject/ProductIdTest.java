package com.inditex.priceschedulerapi.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {

    /**
     * Tests for the {@code ProductId.of(Long value)} method.
     * <p>
     * This method is responsible for creating a new {@code ProductId} instance
     * after validating the provided value.
     */

    @Test
    void shouldCreateProductIdWhenValueIsValid() {
        // Given
        Long validValue = 10L;

        // When
        ProductId productId = ProductId.of(validValue);

        // Then
        assertNotNull(productId, "ProductId should not be null");
        assertEquals(validValue, productId.getValue(), "ProductId value should match the provided value");
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        // Given
        Long nullValue = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductId.of(nullValue),
                "An exception should be thrown when ProductId value is null"
        );
        assertEquals("Product ID cannot be null", exception.getMessage(), "Error message should match the expected message");
    }

    @Test
    void shouldThrowExceptionWhenValueIsZero() {
        // Given
        Long zeroValue = 0L;

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductId.of(zeroValue),
                "An exception should be thrown when ProductId value is zero"
        );
        assertEquals("Product ID must be positive", exception.getMessage(), "Error message should match the expected message");
    }

    @Test
    void shouldThrowExceptionWhenValueIsNegative() {
        // Given
        Long negativeValue = -5L;

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ProductId.of(negativeValue),
                "An exception should be thrown when ProductId value is negative"
        );
        assertEquals("Product ID must be positive", exception.getMessage(), "Error message should match the expected message");
    }
}