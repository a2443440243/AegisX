# AegisX - 新一代企业级插件化架构

<div align="center">

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PF4J](https://img.shields.io/badge/PF4J-3.9.0-blue.svg)](https://github.com/pf4j/pf4j)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Status](https://img.shields.io/badge/维护状态-积极维护中-green.svg)](#)

**AegisX (PF4J Scaffold) 是一个基于 PF4J 和 Spring Boot 构建的、面向下一代开发的智能插件化企业级应用脚手架。**

</div>

AegisX 旨在为企业提供一个高度可扩展、安全可靠、并深度融合 AI 驱动开发模式的现代化应用平台。它不仅继承了 PF4J 的轻量级和高性能，还融入了 Spring Boot 的强大生态，为开发者提供了无与伦比的开发体验和效率。

---

## 目录

- [✨ 核心特性](#-核心特性)
- [🚀 快速开始](#-快速开始)
- [🛠️ 架构设计](#️-架构设计)
- [🧩 插件开发](#-插件开发)
- [🤖 AI 驱动开发](#-ai-驱动开发)
- [🤝 社区与贡献](#-社区与贡献)
- [📄 项目信息](#-项目信息)

---

## ✨ 核心特性

AegisX 提供了一系列企业级特性，旨在提升开发效率、系统稳定性和安全性。

| 特性 | 描述 |
| :--- | :--- |
| **🔥 动态热插拔** | 无需重启应用即可动态加载、更新和卸载插件，实现业务功能的敏捷迭代。 |
| **🛡️ 企业级安全** | 每个插件拥有独立的类加载器，实现资源隔离。结合 Spring Security，提供精细化的权限控制。 |
| **📊 智能监控** | 实时监控插件运行状态、性能指标（内存、CPU），并提供健康检查和自动告警机制。 |
| **🔧 可视化管理** | 提供直观的 Web 控制台，支持插件的批量启停、配置管理和日志查看。 |
| **📚 开发友好** | 丰富的 API、代码生成工具和完善的文档，帮助开发者快速上手。 |
| **🌐 云原生就绪** | 支持容器化部署（Docker/Kubernetes），并与微服务生态（如 Spring Cloud）无缝集成。 |
| **🤖 AI 驱动** | 深度融合 AI 辅助开发，从需求设计到代码生成、测试、文档的全流程赋能。 |

---
## 🚀 快速开始

### 环境要求
- **Java**: 11 或更高版本
- **Maven**: 3.6 或更高版本
- **MySQL**: 5.7 或更高版本

### 安装与运行

**1. 克隆项目**
```bash
git clone https://github.com/a2443440243/AegisX.git
cd AegisX
```

**2. 数据库配置**
首先，在您的 MySQL 实例中创建一个数据库。
```sql
CREATE DATABASE java_admin CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
然后，编辑 `src/main/resources/application.yml` 文件，更新您的数据库连接信息。
```yaml
spring:
  datasource:
    url: jdbc:mysql://192.168.100.221:3306/java_admin # <-- 修改为您的数据库地址
    username: java_admin # <-- 修改为您的用户名
    password: your_password # <-- 修改为您的密码
```

**3. 编译和运行**
```bash
# 清理、编译并安装依赖
mvn clean install

# 启动应用
mvn spring-boot:run
```

**4. 访问系统**
应用启动后，您可以通过以下地址访问：
- **管理控制台**: `http://localhost:8081` (默认账户: `admin` / `147896325a`)
- **API 文档**: `http://localhost:8081/api-docs`
- **健康检查**: `http://localhost:8081/actuator/health`

---

## 🛠️ 架构设计

AegisX 采用先进的微内核架构，结合分层设计理念，确保了系统的高内聚、低耦合和高可扩展性。

### 核心理念
- **微内核 (Microkernel)**: 核心系统保持轻量，仅提供最基础的服务（插件管理、生命周期、扩展点）。所有业务功能均通过插件实现。
- **分层设计 (Layered Architecture)**: 清晰的层次结构（核心层、接口层、插件层、应用层）确保了职责分离和可维护性。
- **AI 驱动 (AI-Driven)**: 将 AI 工具链深度融入开发工作流，赋能开发者，提升效率与质量。

### 系统架构图

![AegisX 系统架构图](docs/AegisX-Architecture.png)

*（图示：展示了核心系统、插件管理器、扩展点接口、安全模块、监控模块以及各个插件之间的关系。）*

### 技术栈

| 技术 | 用途 |
| :--- | :--- |
| **核心框架** | Spring Boot 2.7.14, PF4J 3.9.0 |
| **数据持久化** | Spring Data JPA, Hibernate, MySQL |
| **安全控制** | Spring Security |
| **API 文档** | SpringDoc (OpenAPI 3) |
| **监控** | Spring Boot Actuator |
| **构建工具** | Maven |

---
## 🧩 插件开发

AegisX 的插件开发遵循 PF4J 的标准，同时提供了更丰富的企业级支持。

**完整开发指南**: [插件开发深度指南](docs/PLUGIN_DEVELOPMENT_GUIDE.md)

### 核心扩展点

AegisX 提供了两个核心扩展点接口，插件通过实现这些接口来与主系统交互。

**1. `PluginInterface` - 基础插件接口**
所有插件都必须实现的基础接口，定义了插件的元数据和核心执行逻辑。
```java
public interface PluginInterface extends ExtensionPoint {
    String getPluginName();
    String getPluginVersion();
    Object execute(Object input);
    // ... 其他生命周期方法
}
```

**2. `DatabaseExtension` - 数据库扩展接口**
为需要与主应用数据库交互的插件提供了一套安全的、标准化的数据访问 API。
```java
public interface DatabaseExtension extends ExtensionPoint {
    // 提供 CRUD, 条件查询, 分页, 批量操作等
    <T> T save(T entity);
    <T> Optional<T> findById(Class<T> entityClass, Long id);
    // ... 更多数据操作方法
}
```

### 开发流程概览

1.  **创建插件项目**: 使用提供的 Maven 原型或参考 `database-demo-plugin` 示例。
2.  **实现扩展点**: 编写实现 `PluginInterface` 或 `DatabaseExtension` 的类。
3.  **添加 `@Extension` 注解**: 在您的实现类上添加 `@Extension` 注解，以便 PF4J 能够发现它。
4.  **打包与部署**: 将插件打包为 JAR 文件，并放入主应用的 `plugins` 目录。AegisX 将自动加载并激活它。

### 示例: `database-demo-plugin`

项目内置了一个功能完整的数据库操作插件示例 (`database-demo-plugin`)，展示了企业级插件开发的最佳实践。我们强烈建议您在开始开发前研究此示例。

---

## 🤖 AI 驱动开发

AegisX 不仅仅是一个框架，更是一种先进开发模式的实践平台。我们鼓励团队全面拥抱 AI，将开发效率提升至新的高度。

### AI 辅助开发工作流

我们推荐一个将人类智慧与 AI 效率相结合的开发流程：

1.  **需求与设计 (人 + AI)**:
    *   **开发者**: 提出业务需求。
    *   **AI**: 辅助分析需求、建议技术方案、设计插件架构和接口。

2.  **代码生成 (AI)**:
    *   **AI**: 基于设计，快速生成插件骨架、实现扩展点、编写配置文件和数据模型。

3.  **审查与优化 (人 + AI)**:
    *   **开发者**: 审查 AI 生成的代码，进行业务逻辑的微调和重构。
    *   **AI**: 辅助进行代码质量检查、发现潜在 bug、提出优化建议。

4.  **测试与文档 (AI)**:
    *   **AI**: 自动生成单元测试、集成测试用例，并生成标准的 API 文档和用户手册。

### 推荐 AI 工具链

- **代码生成**: GitHub Copilot, Tabnine, Cursor
- **架构与咨询**: ChatGPT-4, Claude, Trae
- **IDE 集成**: JetBrains AI Assistant, VS IntelliCode

通过这种模式，开发团队可以将精力从重复性工作中解放出来，专注于业务创新和复杂问题的解决，实现 **开发效率数倍提升**。

---
## 🤝 社区与贡献

我们欢迎所有形式的贡献，无论是代码、文档还是问题反馈。

- **问题反馈**: [GitHub Issues](https://github.com/a2443440243/AegisX/issues)
- **技术讨论**: [GitHub Discussions](https://github.com/a2443440243/AegisX/discussions)
- **参与贡献**: 请参考我们的 [贡献指南](CONTRIBUTING.md)。

### 项目地址
- 🐙 **GitHub**: [https://github.com/a2443440243/AegisX](https://github.com/a2443440243/AegisX)
- 📦 **Gitee**: [https://gitee.com/cuixin_1/AegisX](https://gitee.com/cuixin_1/AegisX)

---

## 📄 项目信息

### 发展路线图

**近期计划 (v1.1)**
- [ ] 插件市场与在线安装
- [ ] 可视化插件开发工具
- [ ] 性能监控仪表盘增强

**长期愿景 (v3.0)**
- [ ] 低代码/无代码插件生成平台
- [ ] AI 驱动的智能运维与自愈能力
- [ ] 跨语言插件支持（如 Python, Go）

### 开源协议
本项目采用 [MIT License](LICENSE) 开源协议。

---

<div align="center">
**🚀 AegisX - 赋能企业，拥抱未来 🚀**
</div>
