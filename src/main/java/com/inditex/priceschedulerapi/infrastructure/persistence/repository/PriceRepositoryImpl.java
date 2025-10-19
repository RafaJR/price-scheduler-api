package com.inditex.priceschedulerapi.infrastructure.persistence.repository;

import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.repository.PriceRepository;
import com.inditex.priceschedulerapi.domain.valueobject.BrandId;
import com.inditex.priceschedulerapi.domain.valueobject.ProductId;
import com.inditex.priceschedulerapi.infrastructure.persistence.mapper.PriceEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the domain PriceRepository interface.
 * This class is part of the infrastructure layer and adapts Spring Data JPA
 * to the domain repository contract.
 * Follows the Adapter pattern and Dependency Inversion Principle.
 */
@Component
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final JpaPriceRepositoryAdapter jpaRepository;
    private final PriceEntityMapper mapper;

    @Override
    public List<Price> findApplicablePrices(ProductId productId, BrandId brandId, LocalDateTime applicationDate) {
        return jpaRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId.getValue(),
                brandId.getValue(),
                applicationDate,
                applicationDate
            )
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
