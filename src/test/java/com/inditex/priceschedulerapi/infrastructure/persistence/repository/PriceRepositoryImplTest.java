package com.inditex.priceschedulerapi.infrastructure.persistence.repository;

import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.valueobject.BrandId;
import com.inditex.priceschedulerapi.domain.valueobject.ProductId;
import com.inditex.priceschedulerapi.infrastructure.persistence.entity.PriceEntity;
import com.inditex.priceschedulerapi.infrastructure.persistence.mapper.PriceEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PriceRepositoryImpl.
 * Tests the adapter logic that bridges domain and infrastructure layers.
 */
@ExtendWith(MockitoExtension.class)
class PriceRepositoryImplTest {

    @Mock
    private JpaPriceRepositoryAdapter jpaRepository;

    @Mock
    private PriceEntityMapper mapper;

    @InjectMocks
    private PriceRepositoryImpl priceRepository;

    @Test
    public void testFindApplicablePricesReturnsCorrectPrices() {
        // Arrange
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1);
        LocalDateTime applicationDate = LocalDateTime.of(2023, 6, 14, 10, 0);

        PriceEntity priceEntity = new PriceEntity(); // Mock entity setup
        priceEntity.setProductId(35455L);
        priceEntity.setBrandId(1);
        priceEntity.setStartDate(LocalDateTime.of(2023, 6, 14, 0, 0));
        priceEntity.setEndDate(LocalDateTime.of(2023, 6, 15, 0, 0));

        Price price = Price.of(productId, brandId, null, null, null, null); // Mock Price domain object

        when(jpaRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId.getValue(),
                brandId.getValue(),
                applicationDate,
                applicationDate
        )).thenReturn(List.of(priceEntity));

        when(mapper.toDomain(priceEntity)).thenReturn(price);

        // Act
        List<Price> result = priceRepository.findApplicablePrices(productId, brandId, applicationDate);

        // Assert
        assertThat(result).containsExactly(price);
    }

    @Test
    public void testFindApplicablePricesWhenNoPricesFound() {
        // Arrange
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1);
        LocalDateTime applicationDate = LocalDateTime.of(2023, 6, 14, 10, 0);

        when(jpaRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId.getValue(),
                brandId.getValue(),
                applicationDate,
                applicationDate
        )).thenReturn(List.of());

        // Act
        List<Price> result = priceRepository.findApplicablePrices(productId, brandId, applicationDate);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindApplicablePricesWithMultipleResults() {
        // Arrange
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1);
        LocalDateTime applicationDate = LocalDateTime.of(2023, 6, 14, 10, 0);

        // Mock entities and price objects
        PriceEntity priceEntity1 = new PriceEntity();
        priceEntity1.setProductId(35455L);
        priceEntity1.setBrandId(1);
        priceEntity1.setStartDate(LocalDateTime.of(2023, 6, 14, 0, 0));
        priceEntity1.setEndDate(LocalDateTime.of(2023, 6, 15, 0, 0));

        PriceEntity priceEntity2 = new PriceEntity();
        priceEntity2.setProductId(35455L);
        priceEntity2.setBrandId(1);
        priceEntity2.setStartDate(LocalDateTime.of(2023, 6, 10, 0, 0));
        priceEntity2.setEndDate(LocalDateTime.of(2023, 6, 20, 0, 0));

        Price price1 = Price.of(productId, brandId, null, null, null, null);
        Price price2 = Price.of(productId, brandId, null, null, null, null);

        when(jpaRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId.getValue(),
                brandId.getValue(),
                applicationDate,
                applicationDate
        )).thenReturn(List.of(priceEntity1, priceEntity2));

        when(mapper.toDomain(priceEntity1)).thenReturn(price1);
        when(mapper.toDomain(priceEntity2)).thenReturn(price2);

        // Act
        List<Price> result = priceRepository.findApplicablePrices(productId, brandId, applicationDate);

        // Assert
        assertThat(result).containsExactlyInAnyOrder(price1, price2);
    }
}