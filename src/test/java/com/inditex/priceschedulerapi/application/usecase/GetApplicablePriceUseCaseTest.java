package com.inditex.priceschedulerapi.application.usecase;

import com.inditex.priceschedulerapi.application.dto.PriceQueryRequest;
import com.inditex.priceschedulerapi.application.dto.PriceQueryResponse;
import com.inditex.priceschedulerapi.application.mapper.PriceMapper;
import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.service.PriceSelectionService;
import com.inditex.priceschedulerapi.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class GetApplicablePriceUseCaseTest {

    @Mock
    private PriceSelectionService priceSelectionService;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private GetApplicablePriceUseCase getApplicablePriceUseCase;

    @Test
    void shouldReturnApplicablePriceResponseWhenPriceExists() {
        PriceQueryRequest request = new PriceQueryRequest(1L, 1, LocalDateTime.of(2025, 10, 19, 10, 30));
        Price price = Price.of(
                ProductId.of(1L),
                BrandId.of(1),
                PriceList.of(1001),
                DateRange.of(LocalDateTime.of(2025, 10, 19, 0, 0), LocalDateTime.of(2025, 10, 20, 23, 59)),
                Priority.of(0),
                Money.of(new BigDecimal("35.50"), "EUR")
        );
        PriceQueryResponse response = new PriceQueryResponse(1L, 1, 1001, LocalDateTime.of(2025, 10, 19, 0, 0),
                LocalDateTime.of(2025, 10, 20, 23, 59), new BigDecimal("35.50"), "EUR");

        when(priceMapper.toProductId(request)).thenReturn(ProductId.of(1L));
        when(priceMapper.toBrandId(request)).thenReturn(BrandId.of(1));
        when(priceSelectionService.findApplicablePrice(any(), any(), any()))
                .thenReturn(Optional.of(price));
        when(priceMapper.toResponse(price)).thenReturn(response);

        Optional<PriceQueryResponse> result = getApplicablePriceUseCase.execute(request);

        assertTrue(result.isPresent());
        assertEquals(response, result.get());
    }

    @Test
    void shouldReturnEmptyWhenNoPriceExists() {
        PriceQueryRequest request = new PriceQueryRequest(2L, 2, LocalDateTime.of(2025, 11, 1, 15, 0));

        when(priceMapper.toProductId(request)).thenReturn(ProductId.of(2L));
        when(priceMapper.toBrandId(request)).thenReturn(BrandId.of(2));
        when(priceSelectionService.findApplicablePrice(any(), any(), any()))
                .thenReturn(Optional.empty());

        Optional<PriceQueryResponse> result = getApplicablePriceUseCase.execute(request);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldHandleNullApplicationDateGracefully() {
        PriceQueryRequest request = new PriceQueryRequest(3L, 3, null);

        when(priceMapper.toProductId(request)).thenReturn(ProductId.of(3L));
        when(priceMapper.toBrandId(request)).thenReturn(BrandId.of(3));

        Optional<PriceQueryResponse> result = getApplicablePriceUseCase.execute(request);

        assertTrue(result.isEmpty());
    }
}