# MaxKB MCP服务器

基于Spring Boot实现的MCP (Model Context Protocol) 服务器，用于连接MaxKB知识库并提供知识库查询功能。本服务器支持大模型在被调用的同时，并行查询和检索知识库内容，解决传统大模型和知识库结合有先后顺序的问题。

## 特性

- 基于Spring Boot框架开发
- 支持Spring AI MCP协议
- 使用WebFlux实现响应式编程
- 提供SSE (Server-Sent Events) 接口
- 无缝集成MaxKB知识库
- 并行查询和检索知识库内容

## 前提条件

- JDK 21 或更高版本
- Maven 3.8 或更高版本
- MaxKB 专业版 v1.10.2-lts 或更高版本

## 快速开始

### 1. 配置环境变量

```bash
export MAXKB_API_KEY=your_maxkb_api_key
```

### 2. 构建项目

```bash
mvn clean package
```

### 3. 运行应用

```bash
java -jar target/maxkb-mcp-server-0.0.1-SNAPSHOT.jar
```

或者使用Maven运行:

```bash
mvn spring-boot:run
```

### 4. 访问服务

服务启动后，MCP Inspector可通过以下URL访问:

```
http://localhost:8090/api/v1/sse
```

## 配置说明

配置文件位于`src/main/resources/application.yml`，主要配置项包括:

### 服务器配置

```yaml
server:
  port: 8090  # 服务器端口
```

### MCP服务器配置

```yaml
spring:
  ai:
    mcp:
      server:
        name: maxkb-mcp-server  # MCP服务器名称
        version: 1.0.0  # MCP服务器版本
        type: SYNC  # MCP服务器类型 (SYNC/ASYNC)
        sse-endpoint: /sse  # SSE接口路径
        sse-message-endpoint: /mcp/message  # MCP消息接口路径
        base-url: /api/v1  # API基础路径
```

### MaxKB配置

```yaml
maxkb:
  api-base-url: http://localhost:8080/api  # MaxKB API基础URL
  api-key: ${MAXKB_API_KEY:}  # MaxKB API密钥
  connect-timeout: 5000  # 连接超时时间(毫秒)
  request-timeout: 30000  # 请求超时时间(毫秒)
  max-connections: 20  # 最大连接数
```

## MCP工具列表

本服务器提供以下MCP工具:

1. **searchKnowledgeBase** - 在知识库中搜索信息，根据提供的查询词返回相关文档
2. **getDocumentById** - 根据文档ID获取知识库中的文档详细内容
3. **listKnowledgeBases** - 列出系统中所有可用的知识库
4. **searchInKnowledgeBase** - 在指定的知识库中搜索信息，根据提供的查询词返回相关文档

## 使用Cursor测试MCP工具

在Cursor中，可以通过以下方式测试MCP工具:

1. 配置MCP工具URL: `http://localhost:8090/api/v1/sse`
2. 使用以下格式调用工具:

```
使用searchKnowledgeBase工具在知识库中搜索关于"企业知识库管理"的信息
```

## 许可证

[MIT](LICENSE) 