package com.example.mcpserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Web配置类
 * 配置跨域资源共享(CORS)和其他Web相关配置
 */
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    /**
     * 配置CORS
     * 允许MCP Inspector访问API
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Methods", 
                           "Access-Control-Allow-Headers", "Access-Control-Max-Age",
                           "Access-Control-Request-Headers", "Access-Control-Request-Method")
            .allowCredentials(false)
            .maxAge(3600);
    }
} 