package com.example.mcpserver.service;

import org.springframework.ai.tool.annotation.Tool;

import java.util.List;
import java.util.Map;

/**
 * 知识库服务接口
 * 定义与MaxKB知识库交互的方法
 */
public interface KnowledgeBaseService {
    /**
     * 命中测试列表
     * 根据查询文本在指定知识库中搜索，支持相似度阈值和搜索模式设置
     * 
     * @param datasetId 知识库ID，要搜索的知识库ID
     * @param queryText 查询文本，要搜索的查询文本
     * @param topNumber 返回结果数量限制，默认为10
     * @param similarity 相似度阈值，范围0-1，默认为0.6
     * @param searchMode 搜索模式，可选值: embedding(向量检索)、keywords(关键词检索)或blend(混合检索)，默认为embedding
     * @return 命中结果列表，包含匹配的段落、相似度分数等信息
     */
    @Tool(name = "命中测试列表", description = "在指定的知识库中执行命中测试，返回相关度最高的段落列表。可以指定搜索模式(embedding/keywords/blend)、相似度阈值和返回结果数量。")
    List<Map<String, Object>> getHitTestList(String datasetId, String queryText, Integer topNumber, Double similarity, String searchMode);
} 