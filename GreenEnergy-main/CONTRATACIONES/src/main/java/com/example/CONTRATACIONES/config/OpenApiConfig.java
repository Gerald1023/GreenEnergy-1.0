package com.example.CONTRATACIONES.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
            .info(new Info()
                    .title("API de Contrataciones - GreenEnergy")
                    .version("1.0")
                    .description("Documentación de la API para la gestión de contrataciones en GreenEnergy")
            );
    }
} 