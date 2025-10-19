package com.inditex.priceschedulerapi.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

/**
 * DTO for price query requests.
 * Contains the input parameters required to query for an applicable price.
 *
 * @param productId Product identifier
 * @param brandId Brand identifier
 * @param applicationDate Application date to check price applicability
 */
public record PriceQueryRequest(
        @NotNull(message = "Product ID cannot be null")
        @Positive(message = "Product ID must be positive")
        Long productId,

        @NotNull(message = "Brand ID cannot be null")
        @Positive(message = "Brand ID must be positive")
        Integer brandId,

        @NotNull(message = "Application date cannot be null")
        LocalDateTime applicationDate
) {
}
