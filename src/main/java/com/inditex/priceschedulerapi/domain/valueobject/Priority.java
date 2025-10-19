package com.inditex.priceschedulerapi.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object representing priority level for price selection.
 * Higher values indicate higher priority.
 * Immutable and self-validating.
 */
@Getter
@EqualsAndHashCode
public final class Priority implements Comparable<Priority> {

    private final Integer value;

    private Priority(Integer value) {
        validateValue(value);
        this.value = value;
    }

    public static Priority of(Integer value) {
        return new Priority(value);
    }

    private void validateValue(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("Priority value cannot be null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Priority value cannot be negative");
        }
    }

    @Override
    public int compareTo(Priority other) {
        return this.value.compareTo(other.value);
    }

    public boolean isHigherThan(Priority other) {
        return this.compareTo(other) > 0;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
