package com.inditex.priceschedulerapi.infrastructure.persistence.repository;

import com.inditex.priceschedulerapi.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for PriceEntity.
 * Provides basic CRUD operations and derived queries.
 */
@Repository
public interface JpaPriceRepositoryAdapter extends JpaRepository<PriceEntity, Long> {

    /**
     * Finds all prices for a given product and brand where the application date
     * falls within the start and end date range.
     *
     * Derived query that Spring Data JPA translates to:
     * SELECT * FROM PRICES
     * WHERE PRODUCT_ID = ?
     * AND BRAND_ID = ?
     * AND START_DATE <= ?
     * AND END_DATE >= ?
     *
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @param startDate the application date (compared with startDate)
     * @param endDate the application date (compared with endDate)
     * @return list of matching price entities
     */
    List<PriceEntity> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long productId,
            Integer brandId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
