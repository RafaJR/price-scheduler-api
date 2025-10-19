package com.inditex.priceschedulerapi.domain.model;

import com.inditex.priceschedulerapi.domain.valueobject.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Price aggregate root representing a pricing rule for a product.
 * This is the core domain entity containing business logic.
 * This is NOT the JPA entity - it's a pure domain model without infrastructure dependencies.
 */
@Getter
@EqualsAndHashCode
public class Price {

    private final ProductId productId;
    private final BrandId brandId;
    private final PriceList priceList;
    private final DateRange dateRange;
    private final Priority priority;
    private final Money price;

    private Price(ProductId productId, BrandId brandId, PriceList priceList,
                  DateRange dateRange, Priority priority, Money price) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = priceList;
        this.dateRange = dateRange;
        this.priority = priority;
        this.price = price;
    }

    /**
     * Factory method to create a Price instance.
     */
    public static Price of(ProductId productId, BrandId brandId, PriceList priceList,
                          DateRange dateRange, Priority priority, Money price) {
        return new Price(productId, brandId, priceList, dateRange, priority, price);
    }

    /**
     * Checks if this price is applicable for the given date.
     *
     * @param applicationDate the date to check
     * @return true if the price is applicable on the given date
     */
    public boolean isApplicableOn(LocalDateTime applicationDate) {
        return dateRange.contains(applicationDate);
    }

    /**
     * Determines if this price has higher priority than another price.
     * Used for conflict resolution when multiple prices match.
     *
     * @param other the other price to compare
     * @return true if this price has higher priority
     */
    public boolean hasHigherPriorityThan(Price other) {
        return this.priority.isHigherThan(other.priority);
    }

    /**
     * Checks if this price matches the given product and brand.
     *
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @return true if product and brand match
     */
    public boolean matches(ProductId productId, BrandId brandId) {
        return this.productId.equals(productId) && this.brandId.equals(brandId);
    }
}
