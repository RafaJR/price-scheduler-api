package com.inditex.priceschedulerapi.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTest {

    /**
     * Test class for the DateRange's contains method.
     * The contains method checks if a given date is within the specified range
     * (including the start and end date).
     */

    @Test
    void testContains_withinRange() {
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        DateRange dateRange = DateRange.of(startDate, endDate);

        LocalDateTime testDate = LocalDateTime.of(2025, 6, 15, 12, 0);

        assertTrue(dateRange.contains(testDate), "Date should be within the range");
    }

    @Test
    void testContains_onStartDate() {
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        DateRange dateRange = DateRange.of(startDate, endDate);

        LocalDateTime testDate = startDate;

        assertTrue(dateRange.contains(testDate), "Date should be equal to the start date");
    }

    @Test
    void testContains_onEndDate() {
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        DateRange dateRange = DateRange.of(startDate, endDate);

        LocalDateTime testDate = endDate;

        assertTrue(dateRange.contains(testDate), "Date should be equal to the end date");
    }

    @Test
    void testContains_beforeRange() {
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        DateRange dateRange = DateRange.of(startDate, endDate);

        LocalDateTime testDate = LocalDateTime.of(2024, 12, 31, 23, 59);

        assertFalse(dateRange.contains(testDate), "Date should be before the range");
    }

    @Test
    void testContains_afterRange() {
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        DateRange dateRange = DateRange.of(startDate, endDate);

        LocalDateTime testDate = LocalDateTime.of(2026, 1, 1, 0, 0);

        assertFalse(dateRange.contains(testDate), "Date should be after the range");
    }

    @Test
    void testContains_nullDate() {
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        DateRange dateRange = DateRange.of(startDate, endDate);

        assertThrows(IllegalArgumentException.class, () -> dateRange.contains(null), "Null date should throw IllegalArgumentException");
    }

    @Test
    void testOf_withNullStartDate() {
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> DateRange.of(null, endDate));
        assertEquals("Start date cannot be null", exception.getMessage());
    }

    @Test
    void testOf_withNullEndDate() {
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> DateRange.of(startDate, null));
        assertEquals("End date cannot be null", exception.getMessage());
    }

    @Test
    void testOf_withStartDateAfterEndDate() {
        LocalDateTime startDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 1, 0, 0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> DateRange.of(startDate, endDate));
        assertEquals("Start date must be before or equal to end date", exception.getMessage());
    }

    @Test
    void testOf_withStartDateEqualToEndDate() {
        LocalDateTime startDate = LocalDateTime.of(2025, 6, 15, 12, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 6, 15, 12, 0);

        DateRange dateRange = DateRange.of(startDate, endDate);

        assertNotNull(dateRange);
        assertEquals(startDate, dateRange.getStartDate());
        assertEquals(endDate, dateRange.getEndDate());
    }

    @Test
    void testToString() {
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 10, 30);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 18, 45);
        DateRange dateRange = DateRange.of(startDate, endDate);

        String result = dateRange.toString();

        assertEquals("[2025-01-01T10:30 - 2025-12-31T18:45]", result);
    }
}