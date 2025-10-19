package com.inditex.priceschedulerapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI configuration for API documentation.
 * Configures Swagger UI with API metadata and server information.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI priceSchedulerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Price Scheduler API")
                        .description("REST API for querying product prices based on brand and application date. " +
                                "Implements priority-based price selection when multiple prices match the criteria.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("rafael.jimenez.reina")
                                .email("rafael.jimenez.reina@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local development server")
                ));
    }
}
