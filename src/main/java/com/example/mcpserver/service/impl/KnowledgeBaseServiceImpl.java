package com.example.mcpserver.service.impl;

import com.example.mcpserver.config.MaxKbConfig;
import com.example.mcpserver.service.KnowledgeBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 知识库服务实现类
 * 实现与MaxKB知识库API的交互
 */
@Service
@Slf4j
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final WebClient webClient;
    private final MaxKbConfig maxKbConfig;

    /**
     * 构造函数，初始化WebClient
     *
     * @param maxKbConfig MaxKB配置
     */
    @Autowired
    public KnowledgeBaseServiceImpl(MaxKbConfig maxKbConfig) {
        this.maxKbConfig = maxKbConfig;

        // 创建WebClient，用于调用MaxKB API
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 16MB
                .build();

        this.webClient = WebClient.builder()
                .baseUrl(maxKbConfig.getApiBaseUrl())
                .exchangeStrategies(strategies)
                .defaultHeader("Authorization", maxKbConfig.getApiKey())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    @Tool(name = "hit_test", description = "在指定的知识库中执行命中测试，返回相关度最高的段落列表。可以指定搜索模式(embedding/keywords/blend)、相似度阈值和返回结果数量。")
    public List<Map<String, Object>> getHitTestList(@ToolParam(description = "知识库id") String datasetId,
                                                    @ToolParam(description = "查询文本") String queryText,
                                                    @ToolParam(description = "topN",required = false) Integer topNumber,
                                                    @ToolParam(description = "相似度",required = false) Double similarity,
                                                    @ToolParam(description = "检索模式",required = false) String searchMode) {
        log.info("执行命中测试，知识库ID: {}, 查询文本: {}, topN: {}, 相似度阈值: {}, 搜索模式: {}", 
                datasetId, queryText, topNumber, similarity, searchMode);
        
        // 设置默认值
        int resultLimit = topNumber != null ? topNumber : maxKbConfig.getDefaultSearchLimit();
        double simThreshold = similarity != null ? similarity : maxKbConfig.getDefaultSimilarityThreshold();
        String mode = searchMode != null ? searchMode : maxKbConfig.getDefaultSearchMode();
        
        try {
            // 构建请求URL及参数
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                        .path("/dataset/{datasetId}/hit_test")
                        .queryParam("query_text", queryText)
                        .queryParam("top_number", resultLimit)
                        .queryParam("similarity", simThreshold)
                        .queryParam("search_mode", mode)
                        .build(datasetId))
                    .retrieve()
                    .bodyToMono(ApiResponse.class)
                    .map(ApiResponse::getData)
                    .onErrorResume(e -> {
                        log.error("执行命中测试时出错: {}", e.getMessage(), e);
                        return Mono.just(new ArrayList<>());
                    })
                    .block();
        } catch (Exception e) {
            log.error("执行命中测试时发生异常: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * API响应内部类
     */
    private static class ApiResponse {
        private int code;
        private String message;
        private List<Map<String, Object>> data;
        
        public int getCode() {
            return code;
        }
        
        public void setCode(int code) {
            this.code = code;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
        
        public List<Map<String, Object>> getData() {
            return data;
        }
        
        public void setData(List<Map<String, Object>> data) {
            this.data = data;
        }
    }
} 