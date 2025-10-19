package com.inditex.priceschedulerapi.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Value Object representing a date range with start and end dates.
 * Immutable and self-validating.
 */
@Getter
@EqualsAndHashCode
public final class DateRange {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private DateRange(LocalDateTime startDate, LocalDateTime endDate) {
        validateDates(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static DateRange of(LocalDateTime startDate, LocalDateTime endDate) {
        return new DateRange(startDate, endDate);
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
    }

    /**
     * Checks if the given date is within this date range (inclusive).
     */
    public boolean contains(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Date to check cannot be null");
        }
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    @Override
    public String toString() {
        return String.format("[%s - %s]", startDate, endDate);
    }
}
