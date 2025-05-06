package com.example.mcpserver;

import com.example.mcpserver.service.KnowledgeBaseService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * MaxKB MCP服务器应用程序
 * 提供模型上下文协议(MCP)服务，支持并行知识库查询
 * 
 * 这是应用程序的主入口点
 */
@SpringBootApplication
public class MaxKbMcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaxKbMcpServerApplication.class, args);
    }
    
    /**
     * 注册MCP工具回调提供者
     * 将KnowledgeBaseService中标记了@Tool注解的方法注册为MCP工具
     * 
     * @param knowledgeBaseService 知识库服务
     * @return ToolCallbackProvider 工具回调提供者
     */
    @Bean
    public ToolCallbackProvider toolCallbackProvider(KnowledgeBaseService knowledgeBaseService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(knowledgeBaseService)
                .build();
    }
} 