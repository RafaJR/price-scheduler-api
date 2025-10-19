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

    /**
     * Test for compareTo method when priorities are equal.
     */
    @Test
    void testCompareTo_equal() {
        // Arrange
        Priority priority1 = Priority.of(5);
        Priority priority2 = Priority.of(5);

        // Act
        int result = priority1.compareTo(priority2);

        // Assert
        assertEquals(0, result);
    }

    /**
     * Test for compareTo method when first priority is higher.
     */
    @Test
    void testCompareTo_higherPriority() {
        // Arrange
        Priority priority1 = Priority.of(10);
        Priority priority2 = Priority.of(5);

        // Act
        int result = priority1.compareTo(priority2);

        // Assert
        assertTrue(result > 0);
    }

    /**
     * Test for compareTo method when first priority is lower.
     */
    @Test
    void testCompareTo_lowerPriority() {
        // Arrange
        Priority priority1 = Priority.of(3);
        Priority priority2 = Priority.of(8);

        // Act
        int result = priority1.compareTo(priority2);

        // Assert
        assertTrue(result < 0);
    }

    /**
     * Test for isHigherThan method when priority is higher.
     */
    @Test
    void testIsHigherThan_true() {
        // Arrange
        Priority priority1 = Priority.of(10);
        Priority priority2 = Priority.of(5);

        // Act
        boolean result = priority1.isHigherThan(priority2);

        // Assert
        assertTrue(result);
    }

    /**
     * Test for isHigherThan method when priority is lower.
     */
    @Test
    void testIsHigherThan_false() {
        // Arrange
        Priority priority1 = Priority.of(3);
        Priority priority2 = Priority.of(8);

        // Act
        boolean result = priority1.isHigherThan(priority2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test for isHigherThan method when priorities are equal.
     */
    @Test
    void testIsHigherThan_equal() {
        // Arrange
        Priority priority1 = Priority.of(5);
        Priority priority2 = Priority.of(5);

        // Act
        boolean result = priority1.isHigherThan(priority2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test for toString method.
     */
    @Test
    void testToString() {
        // Arrange
        Priority priority = Priority.of(42);

        // Act
        String result = priority.toString();

        // Assert
        assertEquals("42", result);
    }

}