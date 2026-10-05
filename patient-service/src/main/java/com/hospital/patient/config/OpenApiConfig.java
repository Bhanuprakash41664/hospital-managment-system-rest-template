package com.hospital.patient.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metadata shown at the top of the Swagger UI page.
 */
@Configuration
public class OpenApiConfig {

    @Bean
        public OpenAPI patientServiceOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Patient Service API")
                .description("Manages patient records for the Hospital Management System")
                .version("v1"));
    }
}
