package com.singlestore.singlestore_application.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The OpenApiConfig properties. */

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Singlestore Data Ingestion Pipeline")
                        .version("1.0")
                        .description("This is a spring application ingesting data into kafka, docs by OpenAPI"));
    }
}

