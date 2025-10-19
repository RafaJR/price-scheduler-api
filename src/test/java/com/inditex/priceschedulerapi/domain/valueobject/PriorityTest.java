package com.inditex.priceschedulerapi.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriorityTest {

    /**
     * Test for Priority.of(value) when the value is valid (positive integer).
     */
    @Test
    void testPriorityOfValidValue() {
        // Arrange
        Integer value = 10;

        // Act
        Priority priority = Priority.of(value);

        // Assert
        assertNotNull(priority);
        assertEquals(value, priority.getValue());
    }

    /**
     * Test for Priority.of(value) when the value is zero.
     */
    @Test
    void testPriorityOfZeroValue() {
        // Arrange
        Integer value = 0;

        // Act
        Priority priority = Priority.of(value);

        // Assert
        assertNotNull(priority);
        assertEquals(value, priority.getValue());
    }

    /**
     * Test for Priority.of(value) when the value is null.
     * Expect IllegalArgumentException to be thrown.
     */
    @Test
    void testPriorityOfNullValue() {
        // Arrange
        Integer value = null;

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Priority.of(value));
        assertEquals("Priority value cannot be null", exception.getMessage());
    }

    /**
     * Test for Priority.of(value) when the value is negative.
     * Expect IllegalArgumentException to be thrown.
     */
    @Test
    void testPriorityOfNegativeValue() {
        // Arrange
        Integer value = -5;

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Priority.of(value));
        assertEquals("Priority value cannot be negative", exception.getMessage());
    }

    /**
     * Test for Priority.of(value) to ensure immutability of the Priority object.
     */
    @Test
    void testPriorityImmutability() {
        // Arrange
        Integer value = 15;

        // Act
        Priority priority = Priority.of(value);

        // Assert
        assertNotNull(priority);
        assertEquals(value, priority.getValue());

        // Verify immutability by ensuring attempt to mutate throws compilation error. (Not directly testable in runtime)
    }

}