package com.example.mcpserver.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 应用程序配置类
 * 启用配置属性
 */
@Configuration
@EnableConfigurationProperties(MaxKbConfig.class)
public class AppConfig {
    // 应用程序级别的配置可以添加在这里
} 