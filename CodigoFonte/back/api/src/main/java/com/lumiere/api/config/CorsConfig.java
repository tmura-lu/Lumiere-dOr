package com.lumiere.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica CORS em todas as rotas
                        .allowedOrigins("http://localhost:5173") // Permite o front rodando no Vite
                        .allowedMethods("*") // Permite todos os métodos (GET, POST, PUT, DELETE, etc)
                        .allowedHeaders("*"); // Permite todos os headers
            }
        };
    }
}