package com.inditex.priceschedulerapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration test to verify that the Spring application context loads successfully.
 * This test ensures that all beans are created, configurations are valid,
 * and the database is initialized correctly with schema.sql and data.sql.
 */
@SpringBootTest
class PriceSchedulerApiApplicationTest {

    @Test
    void contextLoads() {
        // This test passes if the Spring application context loads successfully.
        // It validates:
        // - All @Component, @Service, @Repository beans are created
        // - H2 database is initialized
        // - schema.sql creates the PRICES table
        // - data.sql loads the 4 test records
        // - JPA entity mappings are correct
    }

    @Test
    void main() {
        // Test that the main method can be invoked without throwing exceptions.
        // This improves code coverage for the application entry point.
        PriceSchedulerApiApplication.main(new String[]{});
    }
}