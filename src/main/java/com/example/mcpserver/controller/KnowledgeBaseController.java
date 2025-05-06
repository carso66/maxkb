package com.example.mcpserver.controller;

import com.example.mcpserver.service.KnowledgeBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 知识库控制器
 * 提供知识库相关API接口，便于测试
 */
@RestController
@RequestMapping("/api/v1/knowledge-base")
@Slf4j
public class KnowledgeBaseController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    /**
     * 命中测试列表
     * 
     * @param datasetId 知识库ID
     * @param queryText 查询文本
     * @param topNumber 返回结果数量限制
     * @param similarity 相似度阈值
     * @param searchMode 搜索模式
     * @return 命中结果列表
     */
    @GetMapping("/{datasetId}/hit-test")
    public List<Map<String, Object>> getHitTestList(
            @PathVariable String datasetId,
            @RequestParam("query_text") String queryText,
            @RequestParam(value = "top_number", required = false) Integer topNumber,
            @RequestParam(required = false) Double similarity,
            @RequestParam(value = "search_mode", required = false) String searchMode) {
        log.info("API调用: 命中测试列表, 知识库ID: {}, 查询文本: {}, topN: {}, 相似度阈值: {}, 搜索模式: {}", 
                datasetId, queryText, topNumber, similarity, searchMode);
        return knowledgeBaseService.getHitTestList(datasetId, queryText, topNumber, similarity, searchMode);
    }
} 