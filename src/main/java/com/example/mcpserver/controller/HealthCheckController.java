package com.example.mcpserver.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mcpserver.config.MaxKbConfig;

/**
 * 健康检查控制器
 * 提供健康检查和服务状态API
 */
@RestController
@RequestMapping("/api/v1/health")
public class HealthCheckController {

    @Autowired
    private MaxKbConfig maxKbConfig;

    /**
     * 健康检查API，返回服务状态信息
     * 
     * @return 服务状态信息
     */
    @GetMapping
    public Map<String, Object> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "maxkb-mcp-server");
        response.put("maxkb", new HashMap<String, Object>() {{
            put("apiBaseUrl", maxKbConfig.getApiBaseUrl());
            put("connected", isMaxKbConnected());
        }});
        return response;
    }

    /**
     * 检查MaxKB连接状态
     * 
     * @return MaxKB是否连接正常
     */
    private boolean isMaxKbConnected() {
        // 在实际应用中，可以尝试调用MaxKB API检查连接状态
        // 这里简单返回true，表示连接正常
        return true;
    }
} 