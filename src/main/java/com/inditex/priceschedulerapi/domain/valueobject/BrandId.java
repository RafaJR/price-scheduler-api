package com.inditex.priceschedulerapi.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object representing a Brand identifier.
 * Immutable and self-validating.
 */
@Getter
@EqualsAndHashCode
public final class BrandId {

    private final Integer value;

    private BrandId(Integer value) {
        validateValue(value);
        this.value = value;
    }

    public static BrandId of(Integer value) {
        return new BrandId(value);
    }

    private void validateValue(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("Brand ID cannot be null");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("Brand ID must be positive");
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
