package com.inditex.priceschedulerapi.domain.model;

import com.inditex.priceschedulerapi.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Price aggregate root.
 * Tests the business logic for date range applicability.
 */
class PriceTest {

    @Test
    void testIsApplicableOn_WithinDateRange() {
        // Given
        DateRange dateRange = DateRange.of(
                LocalDateTime.of(2025, 10, 1, 0, 0),
                LocalDateTime.of(2025, 10, 31, 23, 59)
        );
        Price price = Price.of(
                ProductId.of(35455L),
                BrandId.of(1),
                PriceList.of(1),
                dateRange,
                Priority.of(1),
                Money.of(BigDecimal.valueOf(100.00), "EUR")
        );
        LocalDateTime applicationDate = LocalDateTime.of(2025, 10, 15, 12, 0);

        // When
        boolean result = price.isApplicableOn(applicationDate);

        // Then
        assertTrue(result);
    }

    @Test
    void testIsApplicableOn_BeforeDateRange() {
        // Given
        DateRange dateRange = DateRange.of(
                LocalDateTime.of(2025, 10, 1, 0, 0),
                LocalDateTime.of(2025, 10, 31, 23, 59)
        );
        Price price = Price.of(
                ProductId.of(35455L),
                BrandId.of(1),
                PriceList.of(1),
                dateRange,
                Priority.of(1),
                Money.of(BigDecimal.valueOf(100.00), "EUR")
        );
        LocalDateTime applicationDate = LocalDateTime.of(2025, 9, 30, 23, 59);

        // When
        boolean result = price.isApplicableOn(applicationDate);

        // Then
        assertFalse(result);
    }

    @Test
    void testIsApplicableOn_AfterDateRange() {
        // Given
        DateRange dateRange = DateRange.of(
                LocalDateTime.of(2025, 10, 1, 0, 0),
                LocalDateTime.of(2025, 10, 31, 23, 59)
        );
        Price price = Price.of(
                ProductId.of(35455L),
                BrandId.of(1),
                PriceList.of(1),
                dateRange,
                Priority.of(1),
                Money.of(BigDecimal.valueOf(100.00), "EUR")
        );
        LocalDateTime applicationDate = LocalDateTime.of(2025, 11, 1, 0, 0);

        // When
        boolean result = price.isApplicableOn(applicationDate);

        // Then
        assertFalse(result);
    }

    @Test
    void testIsApplicableOn_OnStartOfDateRange() {
        // Given
        DateRange dateRange = DateRange.of(
                LocalDateTime.of(2025, 10, 1, 0, 0),
                LocalDateTime.of(2025, 10, 31, 23, 59)
        );
        Price price = Price.of(
                ProductId.of(35455L),
                BrandId.of(1),
                PriceList.of(1),
                dateRange,
                Priority.of(1),
                Money.of(BigDecimal.valueOf(100.00), "EUR")
        );
        LocalDateTime applicationDate = LocalDateTime.of(2025, 10, 1, 0, 0);

        // When
        boolean result = price.isApplicableOn(applicationDate);

        // Then
        assertTrue(result);
    }

    @Test
    void testIsApplicableOn_OnEndOfDateRange() {
        // Given
        DateRange dateRange = DateRange.of(
                LocalDateTime.of(2025, 10, 1, 0, 0),
                LocalDateTime.of(2025, 10, 31, 23, 59)
        );
        Price price = Price.of(
                ProductId.of(35455L),
                BrandId.of(1),
                PriceList.of(1),
                dateRange,
                Priority.of(1),
                Money.of(BigDecimal.valueOf(100.00), "EUR")
        );
        LocalDateTime applicationDate = LocalDateTime.of(2025, 10, 31, 23, 59);

        // When
        boolean result = price.isApplicableOn(applicationDate);

        // Then
        assertTrue(result);
    }
}