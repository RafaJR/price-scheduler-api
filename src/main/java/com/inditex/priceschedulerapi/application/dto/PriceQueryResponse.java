package com.inditex.priceschedulerapi.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for price query responses.
 * Contains the output data returned when querying for an applicable price.
 * Follows the response format specified in the exercise requirements.
 *
 * @param productId Product identifier
 * @param brandId Brand identifier
 * @param priceList Price list identifier (tariff)
 * @param startDate Start date of price applicability
 * @param endDate End date of price applicability
 * @param price Final price to apply
 * @param currency Currency code (ISO)
 */
public record PriceQueryResponse(
        Long productId,
        Integer brandId,
        Integer priceList,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startDate,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {
}
