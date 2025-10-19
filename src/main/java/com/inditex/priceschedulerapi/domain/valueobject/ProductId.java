package com.inditex.priceschedulerapi.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object representing a Product identifier.
 * Immutable and self-validating.
 */
@Getter
@EqualsAndHashCode
public final class ProductId {

    private final Long value;

    private ProductId(Long value) {
        validateValue(value);
        this.value = value;
    }

    public static ProductId of(Long value) {
        return new ProductId(value);
    }

    private void validateValue(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
