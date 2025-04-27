package com.example.softtest2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lab 3 API Swagger Implementation")
                        .description("Documentation for Animal Warehouse API")
                        .version("0.1")
                        .contact(new Contact()
                                .name("Rustam Baimatov")))
                .tags(
                        Arrays.asList(
                                new Tag().name("A - Animal Warehouse").description("Controller for Animal Warehouse."),
                                new Tag().name("B - Users").description("Controller for users."),
                                new Tag().name("C - Animal Orders").description("Controller for orders from animal warehouse."),
                                new Tag().name("W - Swagger").description("Controller for Swagger UI")
                        )
                );


    }





}
