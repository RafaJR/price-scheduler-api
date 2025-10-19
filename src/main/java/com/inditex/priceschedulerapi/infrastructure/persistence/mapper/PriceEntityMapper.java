package com.inditex.priceschedulerapi.infrastructure.persistence.mapper;

import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.valueobject.*;
import com.inditex.priceschedulerapi.infrastructure.persistence.entity.PriceEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert between PriceEntity (infrastructure) and Price (domain).
 * Follows the Dependency Inversion Principle - infrastructure depends on domain, not vice versa.
 */
@Component
public class PriceEntityMapper {

    /**
     * Converts a PriceEntity (JPA) to a Price (domain model).
     */
    public Price toDomain(PriceEntity entity) {
        if (entity == null) {
            return null;
        }

        return Price.of(
                ProductId.of(entity.getProductId()),
                BrandId.of(entity.getBrandId()),
                PriceList.of(entity.getPriceList()),
                DateRange.of(entity.getStartDate(), entity.getEndDate()),
                Priority.of(entity.getPriority()),
                Money.of(entity.getPrice(), entity.getCurrency())
        );
    }

    /**
     * Converts a Price (domain model) to a PriceEntity (JPA).
     */
    public PriceEntity toEntity(Price domain) {
        if (domain == null) {
            return null;
        }

        return PriceEntity.builder()
                .productId(domain.getProductId().getValue())
                .brandId(domain.getBrandId().getValue())
                .priceList(domain.getPriceList().getValue())
                .startDate(domain.getDateRange().getStartDate())
                .endDate(domain.getDateRange().getEndDate())
                .priority(domain.getPriority().getValue())
                .price(domain.getPrice().getAmount())
                .currency(domain.getPrice().getCurrencyCode())
                .build();
    }
}
