package com.smartmedicine.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.cors.allowed-origins}") private String origins;

    @Override
    public void addCorsMappings(CorsRegistry r) {
        r.addMapping("/api/**")
         .allowedOrigins(origins.split("\\s*,\\s*"))
         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
         .allowedHeaders("Content-Type", "X-Workspace-Id")
         .maxAge(3600);
    }
}
