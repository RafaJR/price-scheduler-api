package com.inditex.priceschedulerapi.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceListTest {

    /**
     * Tests for the PriceList class.
     * <p>
     * The PriceList class is a value object that enforces the validation of an associated integer value.
     * It provides a static `of` method for creating PriceList instances.
     * The tests ensure the correct behavior of the `of` method and its associated validations.
     */

    @Test
    void shouldCreatePriceListWhenValueIsPositive() {
        // Arrange
        Integer validValue = 10;

        // Act
        PriceList priceList = PriceList.of(validValue);

        // Assert
        assertNotNull(priceList);
        assertEquals(validValue, priceList.getValue());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        // Arrange
        Integer nullValue = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> PriceList.of(nullValue));
        assertEquals("Price list cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenValueIsZero() {
        // Arrange
        Integer zeroValue = 0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> PriceList.of(zeroValue));
        assertEquals("Price list must be positive", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNegative() {
        // Arrange
        Integer negativeValue = -5;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> PriceList.of(negativeValue));
        assertEquals("Price list must be positive", exception.getMessage());
    }

    @Test
    void shouldReturnStringValueOfPriceList() {
        // Arrange
        Integer validValue = 15;
        PriceList priceList = PriceList.of(validValue);

        // Act
        String stringValue = priceList.toString();

        // Assert
        assertEquals(validValue.toString(), stringValue);
    }
}