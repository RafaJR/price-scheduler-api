package com.inditex.priceschedulerapi.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object representing a price list identifier (tariff).
 * Immutable and self-validating.
 */
@Getter
@EqualsAndHashCode
public final class PriceList {

    private final Integer value;

    private PriceList(Integer value) {
        validateValue(value);
        this.value = value;
    }

    public static PriceList of(Integer value) {
        return new PriceList(value);
    }

    private void validateValue(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("Price list cannot be null");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("Price list must be positive");
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
