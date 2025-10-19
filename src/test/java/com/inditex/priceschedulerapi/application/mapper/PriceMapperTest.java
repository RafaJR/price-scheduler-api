package com.inditex.priceschedulerapi.application.mapper;

import com.inditex.priceschedulerapi.application.dto.PriceQueryRequest;
import com.inditex.priceschedulerapi.application.dto.PriceQueryResponse;
import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceMapperTest {

    private final PriceMapper priceMapper = new PriceMapper();

    @Test
    void testToProductId() {
        // Given
        PriceQueryRequest request = new PriceQueryRequest(
                35455L,
                1,
                LocalDateTime.of(2023, 10, 18, 10, 0)
        );

        // When
        ProductId productId = priceMapper.toProductId(request);

        // Then
        assertEquals(35455L, productId.getValue());
    }

    @Test
    void testToBrandId() {
        // Given
        PriceQueryRequest request = new PriceQueryRequest(
                35455L,
                1,
                LocalDateTime.of(2023, 10, 18, 10, 0)
        );

        // When
        BrandId brandId = priceMapper.toBrandId(request);

        // Then
        assertEquals(1, brandId.getValue());
    }

    @Test
    void testToResponseWithValidPrice() {
        // Given
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1);
        PriceList priceList = PriceList.of(2);
        DateRange dateRange = DateRange.of(
                LocalDateTime.of(2023, 10, 18, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59)
        );
        Priority priority = Priority.of(0);
        Money money = Money.of(new BigDecimal("35.50"), "EUR");
        Price price = Price.of(productId, brandId, priceList, dateRange, priority, money);

        // When
        PriceQueryResponse response = priceMapper.toResponse(price);

        // Then
        assertEquals(35455L, response.productId());
        assertEquals(1, response.brandId());
        assertEquals(2, response.priceList());
        assertEquals(LocalDateTime.of(2023, 10, 18, 0, 0), response.startDate());
        assertEquals(LocalDateTime.of(2023, 12, 31, 23, 59), response.endDate());
        assertEquals(new BigDecimal("35.50"), response.price());
        assertEquals("EUR", response.currency());
    }
}