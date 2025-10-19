package com.inditex.priceschedulerapi.infrastructure.persistence.repository;

import com.inditex.priceschedulerapi.infrastructure.persistence.entity.PriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Rollback
class JpaPriceRepositoryAdapterTest {

    @Autowired
    private JpaPriceRepositoryAdapter repository;

    @Test
    void shouldFindPriceEntityForMatchingCriteria() {
        // Arrange
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1)
                .startDate(LocalDateTime.of(2025, 10, 1, 0, 0))
                .endDate(LocalDateTime.of(2025, 10, 31, 23, 59))
                .priceList(1)
                .productId(100L)
                .priority(0)
                .price(new BigDecimal("49.99"))
                .currency("EUR")
                .build();
        repository.save(priceEntity);

        Long productId = 100L;
        Integer brandId = 1;
        LocalDateTime startDate = LocalDateTime.of(2025, 10, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 10, 15, 23, 59);

        // Act
        List<PriceEntity> results = repository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId,
                brandId,
                startDate,
                endDate
        );

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).usingRecursiveComparison().isEqualTo(priceEntity);
    }

    @Test
    void shouldNotFindPriceEntityWhenStartDateDoesNotMatch() {
        // Arrange
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1)
                .startDate(LocalDateTime.of(2025, 10, 1, 0, 0))
                .endDate(LocalDateTime.of(2025, 10, 31, 23, 59))
                .priceList(1)
                .productId(100L)
                .priority(0)
                .price(new BigDecimal("49.99"))
                .currency("EUR")
                .build();
        repository.save(priceEntity);

        Long productId = 100L;
        Integer brandId = 1;
        LocalDateTime startDate = LocalDateTime.of(2025, 9, 30, 23, 59);
        LocalDateTime endDate = LocalDateTime.of(2025, 10, 31, 23, 59);

        // Act
        List<PriceEntity> results = repository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId,
                brandId,
                startDate,
                endDate
        );

        // Assert
        assertThat(results).isEmpty();
    }

    @Test
    void shouldNotFindPriceEntityWhenEndDateDoesNotMatch() {
        // Arrange
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1)
                .startDate(LocalDateTime.of(2025, 10, 1, 0, 0))
                .endDate(LocalDateTime.of(2025, 10, 31, 23, 59))
                .priceList(1)
                .productId(100L)
                .priority(0)
                .price(new BigDecimal("49.99"))
                .currency("EUR")
                .build();
        repository.save(priceEntity);

        Long productId = 100L;
        Integer brandId = 1;
        LocalDateTime startDate = LocalDateTime.of(2025, 10, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 11, 1, 0, 0);

        // Act
        List<PriceEntity> results = repository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId,
                brandId,
                startDate,
                endDate
        );

        // Assert
        assertThat(results).isEmpty();
    }

    @Test
    void shouldNotFindPriceEntityForNonMatchingProductId() {
        // Arrange
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1)
                .startDate(LocalDateTime.of(2025, 10, 1, 0, 0))
                .endDate(LocalDateTime.of(2025, 10, 31, 23, 59))
                .priceList(1)
                .productId(101L)
                .priority(0)
                .price(new BigDecimal("49.99"))
                .currency("EUR")
                .build();
        repository.save(priceEntity);

        Long productId = 100L;
        Integer brandId = 1;
        LocalDateTime startDate = LocalDateTime.of(2025, 10, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 10, 31, 23, 59);

        // Act
        List<PriceEntity> results = repository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId,
                brandId,
                startDate,
                endDate
        );

        // Assert
        assertThat(results).isEmpty();
    }

    @Test
    void shouldNotFindPriceEntityForNonMatchingBrandId() {
        // Arrange
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(2)
                .startDate(LocalDateTime.of(2025, 10, 1, 0, 0))
                .endDate(LocalDateTime.of(2025, 10, 31, 23, 59))
                .priceList(1)
                .productId(100L)
                .priority(0)
                .price(new BigDecimal("49.99"))
                .currency("EUR")
                .build();
        repository.save(priceEntity);

        Long productId = 100L;
        Integer brandId = 1;
        LocalDateTime startDate = LocalDateTime.of(2025, 10, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 10, 31, 23, 59);

        // Act
        List<PriceEntity> results = repository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId,
                brandId,
                startDate,
                endDate
        );

        // Assert
        assertThat(results).isEmpty();
    }
}