
package com.project.pk48.inventoryms_springboot_api.security.controllers;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.info.Info;

@Configuration
public class swaggerconfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory Management API")
                        .version("1.0")
                        .description("API documentation for managing inventory.")
                        .termsOfService("Terms of service")
                        .contact(new Contact().name("Wilson").email("faithinvest1@gmail.com"))
                        .license(new License().name("01").url("techlines.com")));
    }
}
