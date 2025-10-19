package com.inditex.priceschedulerapi.domain.service;

import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.repository.PriceRepository;
import com.inditex.priceschedulerapi.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PriceSelectionService.
 * Tests the business logic for selecting the correct price based on priority.
 */
@ExtendWith(MockitoExtension.class)
class PriceSelectionServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceSelectionService priceSelectionService;

    @Test
    void findApplicablePrice_shouldReturnEmpty_whenNoPricesFound() {
        // Arrange
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1);
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(priceRepository.findApplicablePrices(productId, brandId, applicationDate))
                .thenReturn(Collections.emptyList());

        // Act
        Optional<Price> result = priceSelectionService.findApplicablePrice(productId, brandId, applicationDate);

        // Assert
        assertFalse(result.isPresent());
        verify(priceRepository, times(1)).findApplicablePrices(productId, brandId, applicationDate);
    }

    @Test
    void findApplicablePrice_shouldReturnOnlyPrice_whenOnePriceFound() {
        // Arrange
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1);
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        Price price = createPrice(productId, brandId, Priority.of(0), BigDecimal.valueOf(35.50));

        when(priceRepository.findApplicablePrices(productId, brandId, applicationDate))
                .thenReturn(List.of(price));

        // Act
        Optional<Price> result = priceSelectionService.findApplicablePrice(productId, brandId, applicationDate);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(price, result.get());
        verify(priceRepository, times(1)).findApplicablePrices(productId, brandId, applicationDate);
    }

    @Test
    void findApplicablePrice_shouldReturnHighestPriority_whenMultiplePricesFound() {
        // Arrange
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1);
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price lowPriorityPrice = createPrice(productId, brandId, Priority.of(0), BigDecimal.valueOf(35.50));
        Price highPriorityPrice = createPrice(productId, brandId, Priority.of(1), BigDecimal.valueOf(25.45));

        when(priceRepository.findApplicablePrices(productId, brandId, applicationDate))
                .thenReturn(List.of(lowPriorityPrice, highPriorityPrice));

        // Act
        Optional<Price> result = priceSelectionService.findApplicablePrice(productId, brandId, applicationDate);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(highPriorityPrice, result.get());
        assertEquals(Priority.of(1), result.get().getPriority());
        verify(priceRepository, times(1)).findApplicablePrices(productId, brandId, applicationDate);
    }

    @Test
    void findApplicablePrice_shouldReturnHighestPriority_whenMultiplePricesWithDifferentPriorities() {
        // Arrange
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1);
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 18, 0);

        Price priority0 = createPrice(productId, brandId, Priority.of(0), BigDecimal.valueOf(35.50));
        Price priority1 = createPrice(productId, brandId, Priority.of(1), BigDecimal.valueOf(30.50));
        Price priority2 = createPrice(productId, brandId, Priority.of(2), BigDecimal.valueOf(25.45));

        when(priceRepository.findApplicablePrices(productId, brandId, applicationDate))
                .thenReturn(List.of(priority0, priority2, priority1)); // Desordenado a propósito

        // Act
        Optional<Price> result = priceSelectionService.findApplicablePrice(productId, brandId, applicationDate);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(priority2, result.get());
        assertEquals(Priority.of(2), result.get().getPriority());
        assertEquals(BigDecimal.valueOf(25.45), result.get().getPrice().getAmount());
        verify(priceRepository, times(1)).findApplicablePrices(productId, brandId, applicationDate);
    }

    /**
     * Helper method to create a Price instance for testing.
     */
    private Price createPrice(ProductId productId, BrandId brandId, Priority priority, BigDecimal amount) {
        return Price.of(
                productId,
                brandId,
                PriceList.of(1),
                DateRange.of(
                        LocalDateTime.of(2020, 6, 14, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59)
                ),
                priority,
                Money.of(amount, "EUR")
        );
    }
}
