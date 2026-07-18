package de.docfaust.myfreezerapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger UI configuration for the MyFreezer API.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Builds the OpenAPI document metadata shown in Swagger UI.
     *
     * @return configured OpenAPI definition
     */
    @Bean
    public OpenAPI myFreezerOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("MyFreezer API")
                        .description("REST API for managing freezer products and categories.")
                        .version("v1"));
    }
}
