package com.inditex.priceschedulerapi.domain.service;

import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.repository.PriceRepository;
import com.inditex.priceschedulerapi.domain.valueobject.BrandId;
import com.inditex.priceschedulerapi.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Domain service responsible for selecting the applicable price based on business rules.
 *
 * Business Rule: When multiple prices are applicable for a product/brand/date combination,
 * the price with the highest priority value should be selected.
 *
 * This service encapsulates domain logic that doesn't naturally fit within a single entity.
 */
@Service
@RequiredArgsConstructor
public class PriceSelectionService {

    private final PriceRepository priceRepository;

    /**
     * Finds the applicable price for a given product, brand and application date.
     *
     * When multiple prices match the criteria, selects the one with highest priority.
     *
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @param applicationDate the date to check price applicability
     * @return Optional containing the selected price, or empty if no price is applicable
     */
    public Optional<Price> findApplicablePrice(ProductId productId, BrandId brandId, LocalDateTime applicationDate) {
        List<Price> applicablePrices = priceRepository.findApplicablePrices(productId, brandId, applicationDate);

        return applicablePrices.stream()
                .max(Comparator.comparing(Price::getPriority));
    }
}
