package com.inditex.priceschedulerapi.application.usecase;

import com.inditex.priceschedulerapi.application.dto.PriceQueryRequest;
import com.inditex.priceschedulerapi.application.dto.PriceQueryResponse;
import com.inditex.priceschedulerapi.application.mapper.PriceMapper;
import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.service.PriceSelectionService;
import com.inditex.priceschedulerapi.domain.valueobject.BrandId;
import com.inditex.priceschedulerapi.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Use case for querying the applicable price for a product, brand and date.
 *
 * This class orchestrates the application flow:
 * 1. Receives a query request (DTO)
 * 2. Converts to domain objects
 * 3. Delegates to domain service for business logic
 * 4. Converts result back to DTO
 *
 * Follows the Application Service pattern in DDD.
 */
@Service
@RequiredArgsConstructor
public class GetApplicablePriceUseCase {

    private final PriceSelectionService priceSelectionService;
    private final PriceMapper priceMapper;

    /**
     * Executes the use case to find the applicable price.
     *
     * @param request the price query request containing productId, brandId and applicationDate
     * @return Optional containing the price response, or empty if no price is found
     */
    public Optional<PriceQueryResponse> execute(PriceQueryRequest request) {
        // Convert DTO to domain value objects
        ProductId productId = priceMapper.toProductId(request);
        BrandId brandId = priceMapper.toBrandId(request);

        // Execute domain logic
        Optional<Price> price = priceSelectionService.findApplicablePrice(
                productId,
                brandId,
                request.applicationDate()
        );

        // Convert domain object to DTO
        return price.map(priceMapper::toResponse);
    }
}
