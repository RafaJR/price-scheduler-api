package com.inditex.priceschedulerapi.infrastructure.config;

import com.inditex.priceschedulerapi.infrastructure.persistence.entity.PriceEntity;
import com.inditex.priceschedulerapi.infrastructure.persistence.repository.JpaPriceRepositoryAdapter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DataInitializerVerifier.
 * Uses mocks to test the CommandLineRunner without starting the full Spring context.
 */
class DataInitializerVerifierTest {

    @Test
    void testRun_whenNoPricesInDatabase_logsEmptyDatabaseWarning() {
        // Arrange
        JpaPriceRepositoryAdapter mockRepository = mock(JpaPriceRepositoryAdapter.class);
        when(mockRepository.count()).thenReturn(0L);

        DataInitializerVerifier verifier = new DataInitializerVerifier(mockRepository);

        // Act
        verifier.run();

        // Assert
        verify(mockRepository, times(1)).count();
        verify(mockRepository, never()).findAll();
    }

    @Test
    void testRun_whenPricesInDatabase_logsDatabaseDetails() {
        // Arrange
        JpaPriceRepositoryAdapter mockRepository = mock(JpaPriceRepositoryAdapter.class);
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1)
                .productId(100L)
                .priceList(1)
                .priority(0)
                .price(BigDecimal.valueOf(123.45))
                .currency("USD")
                .build();

        when(mockRepository.count()).thenReturn(1L);
        when(mockRepository.findAll()).thenReturn(List.of(priceEntity));

        DataInitializerVerifier verifier = new DataInitializerVerifier(mockRepository);

        // Act
        verifier.run();

        // Assert
        verify(mockRepository, times(1)).count();
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void testRun_whenRepositoryThrowsException_shouldPropagateException() {
        // Arrange
        JpaPriceRepositoryAdapter mockRepository = mock(JpaPriceRepositoryAdapter.class);
        when(mockRepository.count()).thenThrow(new RuntimeException("Database error"));

        DataInitializerVerifier verifier = new DataInitializerVerifier(mockRepository);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> verifier.run());

        verify(mockRepository, times(1)).count();
        verify(mockRepository, never()).findAll();
    }
}