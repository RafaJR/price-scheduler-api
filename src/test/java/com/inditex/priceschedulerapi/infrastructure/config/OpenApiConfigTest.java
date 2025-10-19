package com.inditex.priceschedulerapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OpenApiConfigTest {

    @Autowired
    private OpenApiConfig openApiConfig;

    /**
     * Test the priceSchedulerOpenAPI method to verify that it returns an OpenAPI object
     * with the expected information configured.
     */
    @Test
    public void testPriceSchedulerOpenAPI_ConfiguresOpenApiInfoCorrectly() {
        OpenAPI openAPI = openApiConfig.priceSchedulerOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Price Scheduler API", openAPI.getInfo().getTitle());
        assertEquals("REST API for querying product prices based on brand and application date. " +
                        "Implements priority-based price selection when multiple prices match the criteria.",
                openAPI.getInfo().getDescription());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());

        Contact contact = openAPI.getInfo().getContact();
        assertNotNull(contact);
        assertEquals("rafael.jimenez.reina", contact.getName());
        assertEquals("rafael.jimenez.reina@gmail.com", contact.getEmail());

        License license = openAPI.getInfo().getLicense();
        assertNotNull(license);
        assertEquals("Apache 2.0", license.getName());
        assertEquals("https://www.apache.org/licenses/LICENSE-2.0.html", license.getUrl());
    }

    /**
     * Test the priceSchedulerOpenAPI method to verify that it adds the correct server configuration.
     */
    @Test
    public void testPriceSchedulerOpenAPI_ConfiguresServerCorrectly() {
        OpenAPI openAPI = openApiConfig.priceSchedulerOpenAPI();

        assertNotNull(openAPI.getServers());
        assertEquals(1, openAPI.getServers().size());

        Server server = openAPI.getServers().get(0);
        assertNotNull(server);
        assertEquals("http://localhost:8080", server.getUrl());
        assertEquals("Local development server", server.getDescription());
    }
}