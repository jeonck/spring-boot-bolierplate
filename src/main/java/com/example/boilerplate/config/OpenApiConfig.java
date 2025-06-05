package com.example.boilerplate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Boilerplate API")
                        .version("1.0.0")
                        .description("A comprehensive Spring Boot boilerplate API with CRUD operations for Item management. " +
                                   "This API demonstrates modern Spring Boot 3.5.0 features including Virtual Threads support, " +
                                   "REST endpoints, validation, and database operations.")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@example.com")
                                .url("https://github.com/yourusername/spring-boot-boilerplate"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
