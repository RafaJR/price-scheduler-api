package com.inditex.priceschedulerapi.infrastructure.config;

import com.inditex.priceschedulerapi.infrastructure.persistence.repository.JpaPriceRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Verifies that the database has been initialized correctly with sample data.
 * Runs after application startup.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializerVerifier implements CommandLineRunner {

    private final JpaPriceRepositoryAdapter priceRepository;

    @Override
    public void run(String... args) {
        long count = priceRepository.count();
        log.info("===========================================");
        log.info("Database initialization verification");
        log.info("Total prices loaded: {}", count);
        log.info("===========================================");

        if (count == 0) {
            log.warn("WARNING: No prices were loaded into the database!");
        } else {
            log.info("SUCCESS: Database initialized with {} price records", count);
            priceRepository.findAll().forEach(price ->
                log.info("Price loaded: Brand={}, Product={}, PriceList={}, Priority={}, Price={} {}",
                    price.getBrandId(),
                    price.getProductId(),
                    price.getPriceList(),
                    price.getPriority(),
                    price.getPrice(),
                    price.getCurrency())
            );
        }
    }
}
