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
}