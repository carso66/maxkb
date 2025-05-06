package com.example.mcpserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * MaxKB知识库配置类
 * 包含连接MaxKB知识库所需的配置参数
 */
@Configuration
@ConfigurationProperties(prefix = "maxkb")
@Data
public class MaxKbConfig {
    
    /**
     * MaxKB知识库的API基础URL
     */
    private String apiBaseUrl = "http://localhost:8080/api";
    
    /**
     * MaxKB知识库的API密钥
     */
    private String apiKey;
    
    /**
     * 连接超时时间（毫秒）
     */
    private int connectTimeout = 5000;
    
    /**
     * 请求超时时间（毫秒）
     */
    private int requestTimeout = 30000;
    
    /**
     * 最大连接数
     */
    private int maxConnections = 20;
    
    /**
     * 默认搜索结果数量限制 (topN)
     */
    private int defaultSearchLimit = 10;
    
    /**
     * 默认相似度阈值
     */
    private double defaultSimilarityThreshold = 0.6;
    
    /**
     * 默认搜索模式
     * 可选值: embedding, keywords, blend
     */
    private String defaultSearchMode = "embedding";
} 