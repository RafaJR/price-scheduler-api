package com.inditex.priceschedulerapi.domain.repository;

import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.valueobject.BrandId;
import com.inditex.priceschedulerapi.domain.valueobject.ProductId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Price aggregate.
 * This interface is defined in the domain layer following DDD principles.
 * The implementation will be provided by the infrastructure layer.
 */
public interface PriceRepository {

    /**
     * Finds all prices that match the given product, brand and are applicable on the given date.
     *
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @param applicationDate the date to check for applicability
     * @return list of matching prices
     */
    List<Price> findApplicablePrices(ProductId productId, BrandId brandId, LocalDateTime applicationDate);
}
