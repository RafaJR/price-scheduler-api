package com.inditex.priceschedulerapi.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrandIdTest {

    /**
     * Tests for the `of` method in the `BrandId` class.
     * <p>
     * The `BrandId` class represents a value object encapsulating a positive integer brand identifier.
     * The `of` method is a static factory method to create a `BrandId` instance and
     * throws an `IllegalArgumentException` if the input value is null or not positive.
     */

    @Test
    void of_ShouldCreateBrandIdWhenValueIsValid() {
        // Given
        Integer validValue = 5;

        // When
        BrandId brandId = BrandId.of(validValue);

        // Then
        assertNotNull(brandId);
        assertEquals(validValue, brandId.getValue());
    }

    @Test
    void of_ShouldThrowExceptionWhenValueIsNull() {
        // Given
        Integer nullValue = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> BrandId.of(nullValue));

        assertEquals("Brand ID cannot be null", exception.getMessage());
    }

    @Test
    void of_ShouldThrowExceptionWhenValueIsZero() {
        // Given
        Integer zeroValue = 0;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> BrandId.of(zeroValue));

        assertEquals("Brand ID must be positive", exception.getMessage());
    }

    @Test
    void of_ShouldThrowExceptionWhenValueIsNegative() {
        // Given
        Integer negativeValue = -10;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> BrandId.of(negativeValue));

        assertEquals("Brand ID must be positive", exception.getMessage());
    }

    @Test
    void toString_ShouldReturnStringRepresentationOfValue() {
        // Given
        Integer value = 42;
        BrandId brandId = BrandId.of(value);

        // When
        String result = brandId.toString();

        // Then
        assertEquals("42", result);
    }
}