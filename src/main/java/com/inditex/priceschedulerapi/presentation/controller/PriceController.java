package com.inditex.priceschedulerapi.presentation.controller;

import com.inditex.priceschedulerapi.application.dto.PriceQueryRequest;
import com.inditex.priceschedulerapi.application.dto.PriceQueryResponse;
import com.inditex.priceschedulerapi.application.usecase.GetApplicablePriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST Controller for price queries.
 * Exposes endpoints to query applicable prices based on product, brand and date.
 */
@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
@Validated
@Tag(name = "Prices", description = "Price query operations")
public class PriceController {

    private final GetApplicablePriceUseCase getApplicablePriceUseCase;

    /**
     * Queries the applicable price for a given product, brand and application date.
     *
     * @param productId Product identifier
     * @param brandId Brand identifier
     * @param applicationDate Date and time when the price should be applicable
     * @return ResponseEntity with the applicable price or 404 if not found
     */
    @GetMapping
    @Operation(
            summary = "Get applicable price",
            description = "Retrieves the applicable price for a product and brand at a specific date and time. " +
                          "If multiple prices match, returns the one with highest priority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Price found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceQueryResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No applicable price found for the given criteria"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters"
            )
    })
    public ResponseEntity<PriceQueryResponse> getApplicablePrice(
            @Parameter(description = "Product identifier", required = true, example = "35455")
            @RequestParam
            @NotNull(message = "Product ID cannot be null")
            @Positive(message = "Product ID must be positive")
            Long productId,

            @Parameter(description = "Brand identifier", required = true, example = "1")
            @RequestParam
            @NotNull(message = "Brand ID cannot be null")
            @Positive(message = "Brand ID must be positive")
            Integer brandId,

            @Parameter(
                    description = "Application date and time (ISO format)",
                    required = true,
                    example = "2020-06-14T10:00:00"
            )
            @RequestParam
            @NotNull(message = "Application date cannot be null")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime applicationDate
    ) {
        PriceQueryRequest request = new PriceQueryRequest(productId, brandId, applicationDate);

        return getApplicablePriceUseCase.execute(request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
