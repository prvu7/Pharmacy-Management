package com.mpp.pharmacy.Security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${app.security.api-key-name}")
    private String apiKeyName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pharmacy Management API")
                        .version("1.0")
                        .description("API documentation for Pharmacy Management System"))
                .addSecurityItem(new SecurityRequirement().addList(apiKeyName))
                .components(new Components()
                        .addSecuritySchemes(apiKeyName, new SecurityScheme()
                                .name(apiKeyName)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)));
    }
}