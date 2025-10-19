package com.inditex.priceschedulerapi.infrastructure.persistence.mapper;

import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.valueobject.*;
import com.inditex.priceschedulerapi.infrastructure.persistence.entity.PriceEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceEntityMapperTest {

    private final PriceEntityMapper priceEntityMapper = new PriceEntityMapper();

    /**
     * Test to verify that a valid PriceEntity is correctly mapped to its corresponding domain Price object.
     */
    @Test
    void testToDomain_withValidPriceEntity_shouldReturnDomainObject() {
        // Arrange: Create a valid PriceEntity
        PriceEntity priceEntity = PriceEntity.builder()
                .id(1L)
                .brandId(1)
                .productId(35455L)
                .priceList(2)
                .startDate(LocalDateTime.of(2025, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2025, 12, 31, 23, 59))
                .priority(1)
                .price(BigDecimal.valueOf(49.99))
                .currency("EUR")
                .build();

        // Act: Map the PriceEntity to the domain object
        Price result = priceEntityMapper.toDomain(priceEntity);

        // Assert: Verify the mapping is correct
        assertNotNull(result);
        assertEquals(ProductId.of(35455L), result.getProductId());
        assertEquals(BrandId.of(1), result.getBrandId());
        assertEquals(PriceList.of(2), result.getPriceList());
        assertEquals(DateRange.of(
                LocalDateTime.of(2025, 1, 1, 0, 0),
                LocalDateTime.of(2025, 12, 31, 23, 59)), result.getDateRange());
        assertEquals(Priority.of(1), result.getPriority());
        assertEquals(Money.of(BigDecimal.valueOf(49.99), "EUR"), result.getPrice());
    }

    /**
     * Test to verify that null input in the toDomain method returns null as per the method logic.
     */
    @Test
    void testToDomain_withNullEntity_shouldReturnNull() {
        // Act: Map a null PriceEntity
        Price result = priceEntityMapper.toDomain(null);

        // Assert: Verify the result is null
        assertNull(result);
    }

    /**
     * Test to verify that null productId triggers validation when mapping to domain.
     */
    @Test
    void testToDomain_withNullProductId_shouldThrowException() {
        // Arrange: Create a PriceEntity with null productId
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1)
                .priceList(2)
                .startDate(LocalDateTime.of(2025, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2025, 12, 31, 23, 59))
                .priority(1)
                .price(BigDecimal.valueOf(49.99))
                .currency("EUR")
                .build();

        // Act & Assert: Verify that ProductId.of(null) throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> priceEntityMapper.toDomain(priceEntity));
    }

    /**
     * Test to verify that null priceList triggers validation when mapping to domain.
     */
    @Test
    void testToDomain_withNullPriceList_shouldThrowException() {
        // Arrange: Create a PriceEntity with null priceList
        PriceEntity priceEntity = PriceEntity.builder()
                .id(1L)
                .brandId(1)
                .productId(35455L)
                .startDate(LocalDateTime.of(2025, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2025, 12, 31, 23, 59))
                .priority(1)
                .price(BigDecimal.valueOf(49.99))
                .currency("EUR")
                .build();

        // Act & Assert: Verify that PriceList.of(null) throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> priceEntityMapper.toDomain(priceEntity));
    }

    /**
     * Test to verify that a valid Price domain object is correctly mapped to PriceEntity.
     */
    @Test
    void testToEntity_withValidPrice_shouldReturnPriceEntity() {
        // Arrange: Create a valid Price domain object
        Price price = Price.of(
                ProductId.of(35455L),
                BrandId.of(1),
                PriceList.of(2),
                DateRange.of(
                        LocalDateTime.of(2025, 1, 1, 0, 0),
                        LocalDateTime.of(2025, 12, 31, 23, 59)
                ),
                Priority.of(1),
                Money.of(BigDecimal.valueOf(49.99), "EUR")
        );

        // Act: Map the Price to PriceEntity
        PriceEntity result = priceEntityMapper.toEntity(price);

        // Assert: Verify the mapping is correct
        assertNotNull(result);
        assertEquals(35455L, result.getProductId());
        assertEquals(1, result.getBrandId());
        assertEquals(2, result.getPriceList());
        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), result.getStartDate());
        assertEquals(LocalDateTime.of(2025, 12, 31, 23, 59), result.getEndDate());
        assertEquals(1, result.getPriority());
        assertEquals(BigDecimal.valueOf(49.99), result.getPrice());
        assertEquals("EUR", result.getCurrency());
    }

    /**
     * Test to verify that null input in the toEntity method returns null.
     */
    @Test
    void testToEntity_withNullPrice_shouldReturnNull() {
        // Act: Map a null Price
        PriceEntity result = priceEntityMapper.toEntity(null);

        // Assert: Verify the result is null
        assertNull(result);
    }
}