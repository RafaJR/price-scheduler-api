package com.inditex.priceschedulerapi.application.mapper;

import com.inditex.priceschedulerapi.application.dto.PriceQueryRequest;
import com.inditex.priceschedulerapi.application.dto.PriceQueryResponse;
import com.inditex.priceschedulerapi.domain.model.Price;
import com.inditex.priceschedulerapi.domain.valueobject.BrandId;
import com.inditex.priceschedulerapi.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert between DTOs (application layer) and domain objects.
 * Follows the principle of keeping domain logic separate from API concerns.
 */
@Component
public class PriceMapper {

    /**
     * Converts a PriceQueryRequest DTO to domain value objects.
     *
     * @param request the price query request
     * @return ProductId extracted from the request
     */
    public ProductId toProductId(PriceQueryRequest request) {
        return ProductId.of(request.productId());
    }

    /**
     * Converts a PriceQueryRequest DTO to domain value objects.
     *
     * @param request the price query request
     * @return BrandId extracted from the request
     */
    public BrandId toBrandId(PriceQueryRequest request) {
        return BrandId.of(request.brandId());
    }

    /**
     * Converts a Price domain object to a PriceQueryResponse DTO.
     *
     * @param price the domain price object
     * @return the response DTO
     */
    public PriceQueryResponse toResponse(Price price) {
        return new PriceQueryResponse(
                price.getProductId().getValue(),
                price.getBrandId().getValue(),
                price.getPriceList().getValue(),
                price.getDateRange().getStartDate(),
                price.getDateRange().getEndDate(),
                price.getPrice().getAmount(),
                price.getPrice().getCurrencyCode()
        );
    }
}
