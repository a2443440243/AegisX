# 🚀 AegisX — 企业级插件开发框架：拥抱AI时代，赋能高效开发

> **🌟 让AI成为代码的第一生产力 | 革命性提升开发效率**
>
> **💡 核心理念：AegisX 提供坚实的架构基础，让开发者可以轻松集成和利用AI工具，专注于创新而非重复劳动**

中文 | [English](README.md)

---

## 🎯 为什么选择 AegisX？

在数字化浪潮之巅，唯一不变的就是变化本身。AegisX 重新定义了企业级软件的构建方式，它不仅是一个插件框架，更是一套面向未来的、高可用、自适应的架构解决方案。

### 🚀 拥抱AI驱动的开发模式
- **🔌 灵活的AI工具集成**: AegisX的插件化架构允许您轻松地将各种AI代码生成工具集成到您的开发流程中。
- **⚡ 提升开发效率**: 借助外部AI工具，开发者可以快速生成样板代码、数据模型和业务逻辑，从而将更多时间投入到核心业务创新上。
- **🧩 专注于业务逻辑**: AegisX为您处理好了底层架构、插件生命周期和依赖管理，让您可以专注于实现核心业务功能。
- **💡 激发创新**: 通过将重复性编码工作交给AI，您的团队可以探索更具创造性的解决方案。

### ☁️ 为云原生而生
- **DevOps 友好**: 标准化的Maven项目结构与构建流程，可无缝集成到任何CI/CD流水线中。
- **内建健康监控**: 集成Spring Boot Actuator，提供开箱即用的应用监控、健康检查和度量端点，简化运维。
- **容器化就绪**: 简洁的架构和明确的依赖，极易进行容器化部署（如Docker, Kubernetes）。

### 🛡️ 坚不可摧的企业级基座
- **工业级稳定性**: 基于全球最受信赖的Java生态——Spring Boot，为您提供经过海量生产环境验证的稳定性和可靠性。
- **企业级安全**: 深度集成Spring Security，提供可扩展的、强大的认证与授权体系，为您的核心资产保驾护航。
- **高性能数据访问**: 集成Spring Data JPA，提供高效、灵活、标准化的数据持久化解决方案。

### 🌐 面向未来的无限集成
- **架构中立性**: 解耦的插件化设计，使其可以作为“万能粘合剂”，轻松集成AI服务、大数据平台、消息队列、物联网（IoT）设备等任何异构系统。
- **赋能AI集成**: AegisX的开放架构使其成为集成外部AI服务的理想平台。无论是连接大型语言模型（LLM）、集成机器学习管道，还是构建智能自动化流程，都变得轻而易举。

---

## 🏛️ 架构设计

### 🎯 核心架构分层

```
┌─────────────────────────────────────────────────────────────────────┐
│                   🧩 业务能力插件层 (Business Capability Plugin Layer)    │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Database Demo│Custom Plugin│Business Plugin│      ...           │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   ⚙️ 动态插件引擎 (Dynamic Plugin Engine)              │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Plugin Service│PF4J Manager │Lifecycle Mgmt│ Plugin Controller  │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   🚌 核心服务总线 (Core Service Bus)                  │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Security Config│JPA Service │System Service│ Actuator Monitor   │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   📡 统一API网关 (Unified API Gateway)                  │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Plugin API   │System API   │Web Controller│ API Documentation  │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   🔧 企业级技术基座 (Enterprise Technology Foundation)     │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Spring Boot  │   PF4J 3.9  │Spring Security│     MySQL 8.0+    │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
```

### 🏗️ 微内核架构设计
- **核心框架**：基于 PF4J 3.9.0 构建的轻量级内核
- **插件系统**：支持动态加载、卸载和热更新（30秒扫描间隔）
- **扩展点机制**：灵活的接口定义和实现
- **依赖隔离**：每个插件拥有独立的类加载器
- **Spring 集成**：通过 pf4j-spring 实现与 Spring Boot 的无缝集成

### 📊 系统架构图
```
┌─────────────────────────────────────────────────────────┐
│                    Web 管理界面                          │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │  插件管理   │ │  API 文档   │ │  系统监控   │      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                   REST API 层                          │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginController│ ApiController│HealthController│    │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                    业务服务层                           │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginService│ │UserService  │ │CommonService│      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                   数据访问层                            │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │JavaAdminRepo│ │DatabaseUtil │ │  JPA/ORM   │      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                    插件管理层                           │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginManager│ │ExtensionMgr │ │ClassLoader  │      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                    扩展点接口                           │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginInterface│DatabaseExt  │ CustomExt   │      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                     插件实例                            │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │database-demo│ │ custom-plugin│ │future-plugin│     │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
└─────────────────────────────────────────────────────────┘
```

### 🧩 核心模块解析

#### 🔌 插件管理层 (Plugin Management Layer)
- **动态插件加载**: 基于PF4J框架，支持插件的动态加载和卸载
- **插件生命周期**: 完整的插件启动、停止、重新加载管理
- **插件服务**: 统一的插件管理服务，提供插件信息查询和操作接口

#### 🌐 业务服务层 (Business Service Layer)
- **安全认证**: 基于Spring Security的用户认证和权限管理
- **数据持久化**: JPA + MySQL的完整数据访问层
- **系统监控**: 集成Actuator提供应用健康检查和监控端点

---

## 🛠️ 技术栈

### 核心框架
- **Java 11**：现代 Java 开发平台
- **Spring Boot 2.7.x**：企业级微服务框架
- **PF4J 3.9.0**：插件框架核心引擎
- **pf4j-spring 0.8.0**：Spring 集成适配器

### 数据层技术
- **Spring Data JPA**：ORM 数据访问层
- **Hibernate**：JPA 实现
- **MySQL 8.0.33**：生产环境数据库
- **H2 Database**：开发测试数据库
- **HikariCP**：高性能连接池

### Web 层技术
- **Spring MVC**：RESTful API 框架
- **Thymeleaf**：服务端模板引擎
- **Jackson**：JSON 序列化/反序列化
- **Spring Security**：安全认证框架
- **Bootstrap + jQuery**：前端UI框架

### 开发工具链
- **Maven**：项目构建和依赖管理
- **Spring Boot DevTools**：开发时热重载
- **Lombok**：减少样板代码
- **Bean Validation**：数据校验框架
- **Logback**：企业级日志框架

### 运维监控
- **Spring Boot Actuator**：应用监控端点
- **Health Check**：健康检查机制
- **Metrics**：性能指标收集
- **Hot Deployment**：插件热部署支持

| 技术领域 | 核心技术 | 版本 | 作用 |
|:---:|:---:|:---:|:---|
| **后端框架** | Spring Boot | 2.7.14 | 企业级应用基础框架 |
| **插件引擎** | PF4J | 3.9.0 | 动态插件管理核心 |
| **安全框架** | Spring Security | 5.7.x | 认证授权安全防护 |
| **数据库** | MySQL | 8.0+ | 关系型数据库 |
| **ORM框架** | Spring Data JPA | 2.7.x | 数据访问层 |
| **模板引擎** | Thymeleaf | 3.0.x | Web页面渲染 |
| **监控组件** | Spring Actuator | 2.7.x | 应用健康监控 |
| **JVM** | OpenJDK | 11+ | Java运行时环境 |

---

## 🚀 快速启动

### ✅ 环境要求
- JDK 11+
- Maven 3.6+
- MySQL 8.0+

### 🛠️ 启动步骤

```bash
# 1. 克隆项目
git clone https://github.com/a2443440243/AegisX.git
cd AegisX

# 2. 配置数据库
#    打开 src/main/resources/application.yml
#    修改 spring.datasource 下的url, username, password

# 3. Maven构建
mvn clean compile

# 4. 启动应用
mvn spring-boot:run
```

### 🌐 访问入口
- **管理控制台**: `http://localhost:8081`
  - 默认账户: `admin` / `147896325a`
- **API文档**: `http://localhost:8081/api-docs`
- **系统健康检查**: `http://localhost:8081/actuator/health`
- **插件管理API**: `http://localhost:8081/api/plugins`
- **系统状态API**: `http://localhost:8081/api/system/status`

#### 🛠️ 详细安装步骤

##### 1. 克隆项目
```bash
git clone https://github.com/a2443440243/AegisX.git
cd AegisX
```

##### 2. 数据库配置
创建数据库并配置连接信息：
```sql
CREATE DATABASE java_admin CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

编辑 `src/main/resources/application.yml`：
```yaml
spring:
  application:
    name: pf4j-scaffold
  datasource:
    url: jdbc:mysql://192.168.100.221:3306/java_admin
    username: java_admin
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL8Dialect

# 插件配置
plugin:
  path: plugins
  development:
    mode: true
  scan:
    interval: 30

server:
  port: 8081
```

##### 3. 编译和运行
```bash
# 清理并编译项目
mvn clean compile

# 安装依赖
mvn install

# 启动应用
mvn spring-boot:run
```

##### 4. 验证安装
访问主页面，您应该能看到：
- 系统运行状态
- 已加载的插件列表
- 插件管理功能
- 实时监控数据

---

## 🧩 插件开发

### 开发概述

AegisX 基于 PF4J 框架提供了企业级的插件开发能力，支持动态加载、热更新和依赖隔离。通过标准化的扩展点接口，开发者可以安全、高效地扩展系统功能。

**完整开发指南**：[插件开发指南](docs/PLUGIN_DEVELOPMENT_GUIDE.md)

### 核心扩展点

#### 1. PluginInterface - 基础插件接口
```java
public interface PluginInterface extends ExtensionPoint {
    String getPluginName();           // 插件名称
    String getPluginVersion();        // 插件版本
    String getPluginDescription();    // 插件描述
    Object execute(Object input);     // 执行插件功能
    void initialize();                // 初始化方法
    void destroy();                   // 销毁方法
}
```

#### 2. DatabaseExtension - 数据库扩展接口
提供统一的数据库访问能力，包括：
- **CRUD 操作**：save, findById, findAll, deleteById 等
- **条件查询**：findByUsername, findByEmail, findByStatus 等
- **分页查询**：支持排序和分页的高级查询
- **批量操作**：saveAll, deleteByIds, batchUpdateStatus 等
- **统计功能**：count, countByStatus, getUserStatistics 等

### 示例：database-demo-plugin

项目包含完整的插件开发示例，展示企业级插件的标准实现：

#### 项目结构
```
database-demo-plugin/
├── pom.xml                           # Maven 依赖配置
├── README.md                         # 插件说明文档
└── src/main/
    ├── java/com/example/plugin/database/
    │   ├── DatabaseDemoPlugin.java   # 插件主类（生命周期）
    │   └── DatabaseDemoExtension.java # 扩展点实现
    └── resources/
        └── plugin.properties         # 插件元数据配置
```

#### 核心实现特性

**1. 插件生命周期管理**
- `start()`：插件启动时的初始化逻辑
- `stop()`：插件停止时的清理工作
- `delete()`：插件卸载时的资源释放

**2. 数据库扩展实现**
- 模拟用户验证和权限检查
- 演示数据库操作的标准流程
- 展示错误处理和日志记录

**3. 配置管理**
```properties
plugin.id=database-demo-plugin
plugin.class=com.example.plugin.database.DatabaseDemoPlugin
plugin.version=1.0.0
plugin.provider=AegisX Team
plugin.dependencies=
plugin.description=数据库操作演示插件
```

#### 开发学习路径

1. **基础结构**：理解插件的目录结构和配置文件
2. **接口实现**：学习如何实现标准扩展点接口
3. **生命周期**：掌握插件的启动、运行、停止流程
4. **数据访问**：了解如何安全地访问主应用的数据层
5. **错误处理**：学习插件开发中的异常处理最佳实践
6. **测试调试**：掌握插件的本地开发和调试技巧

这个示例为企业级插件开发提供了完整的参考模板和最佳实践指导。

### 📘 开发流程概览

1.  **创建插件项目**: 使用我们提供的Maven原型或参照`database-demo-plugin`示例项目结构。
2.  **定义扩展点**: 在插件中定义`ExtensionPoint`接口，这是插件与主应用交互的契约。
3.  **实现扩展**: 编写实现`@Extension`注解的类，实现具体的业务逻辑。
4.  **打包插件**: 将插件打包成一个独立的JAR文件。
5.  **动态部署**: 将插件JAR包放入主应用的`plugins`目录，AegisX将自动加载并激活它。

### 🔥 系统特性

#### 🔥 动态热插拔
- **运行时加载**：无需重启应用即可加载新插件
- **热更新支持**：30秒自动扫描插件目录变化
- **版本管理**：支持插件版本升级和回滚
- **依赖检查**：自动解析和验证插件依赖关系

#### 🛡️ 企业级安全
- **类加载隔离**：每个插件拥有独立的类加载器
- **权限控制**：基于 Spring Security 的访问控制
- **数据隔离**：插件间数据访问完全隔离
- **安全审计**：完整的插件操作日志记录

#### 📊 智能监控
- **实时状态**：插件运行状态实时监控
- **性能指标**：内存使用、执行时间等关键指标
- **健康检查**：自动检测插件健康状态
- **告警机制**：异常情况自动告警和恢复

#### 🔧 可视化管理
- **Web 控制台**：直观的插件管理界面
- **批量操作**：支持插件的批量启动、停止、更新
- **配置管理**：在线编辑插件配置参数
- **日志查看**：实时查看插件运行日志

#### 📚 开发友好
- **丰富 API**：完整的插件开发 API 和扩展点
- **代码生成**：插件模板和脚手架工具
- **调试支持**：开发模式下的热重载和调试
- **文档完善**：详细的开发指南和示例代码

#### 🌐 云原生就绪
- **容器化支持**：Docker 和 Kubernetes 部署
- **微服务架构**：与 Spring Cloud 生态无缝集成
- **配置中心**：支持外部配置管理
- **服务发现**：自动注册和发现插件服务

---

## 🤖 践行下一代开发模式

### AI 驱动的企业级开发

AegisX 作为新一代企业级插件架构，不仅提供了强大的技术能力，更重要的是为企业践行 AI 驱动的开发模式提供了理想的平台。我们鼓励开发团队充分利用 AI 编程助手，实现开发效率和代码质量的双重提升。

### 🎯 AI 辅助插件开发工作流

#### 1. 需求分析与架构设计
```
开发者 + AI 协作流程：
├── 业务需求分析 → AI 帮助梳理功能点和技术方案
├── 插件架构设计 → AI 推荐最佳实践和设计模式
├── 接口定义 → AI 生成标准化的扩展点接口
└── 技术选型 → AI 提供技术栈建议和风险评估
```

#### 2. 代码生成与实现
- **插件骨架生成**：基于 `database-demo-plugin` 模板，AI 快速生成新插件结构
- **扩展点实现**：AI 辅助实现 `PluginInterface` 和 `DatabaseExtension` 接口
- **配置文件生成**：自动生成 `plugin.properties` 和 Maven 配置
- **数据模型设计**：基于 `JavaAdmin` 实体，AI 设计新的数据模型

#### 3. 质量保证与优化
- **代码审查**：AI 检查代码规范、安全漏洞和性能问题
- **测试生成**：自动生成单元测试、集成测试和端到端测试
- **性能优化**：AI 分析插件性能瓶颈并提供优化建议
- **文档生成**：自动生成 API 文档、使用指南和部署文档

### 🛠️ 推荐的 AI 开发工具链

#### 代码生成与补全
- **GitHub Copilot**：智能代码补全和函数生成
- **Tabnine**：基于上下文的代码建议
- **Cursor**：AI 驱动的代码编辑器
- **CodeWhisperer**：Amazon 的 AI 编程助手
- **Trae**：基于 Claude 的智能编程助手

#### 架构设计与问题解决
- **ChatGPT-4**：架构设计和复杂问题解决
- **Claude**：代码审查和重构建议
- **Bard**：技术方案评估和选型建议

#### 专业开发工具
- **JetBrains AI Assistant**：集成在 IntelliJ IDEA 中的 AI 助手
- **Visual Studio IntelliCode**：智能代码建议和重构

### 📋 AI 协作开发最佳实践

#### 阶段一：项目初始化
```bash
# 1. 与 AI 讨论插件需求
"我需要开发一个用户权限管理插件，基于 AegisX 框架..."

# 2. AI 生成项目结构
# 3. 配置开发环境
mvn archetype:generate -DgroupId=com.example.plugin
```

#### 阶段二：核心开发
```java
// AI 辅助生成插件主类
@Extension
public class UserPermissionPlugin implements PluginInterface {
    // AI 生成的标准实现
}
```

#### 阶段三：测试与部署
```bash
# AI 生成的测试脚本
mvn test
mvn package
# 部署到 plugins 目录
```

### 🚀 企业级开发效率提升

通过 AI 驱动的开发模式，企业团队可以实现：

- **开发效率提升 60%**：自动化重复性编码工作
- **代码质量提升 40%**：AI 辅助的代码审查和优化
- **文档完整性 90%+**：自动生成的技术文档
- **测试覆盖率 85%+**：AI 生成的全面测试用例
- **维护成本降低 50%**：标准化的代码结构和文档

### 💡 未来发展方向

- **智能插件推荐**：基于业务需求自动推荐合适的插件
- **自动化测试**：AI 驱动的插件兼容性和性能测试
- **智能运维**：AI 监控插件运行状态并自动优化
- **代码生成平台**：可视化的插件开发和部署平台

AegisX 致力于成为企业数字化转型中 AI 驱动开发的最佳实践平台。

**开发流程示例:**

1.  **定义接口**: 在AegisX中定义插件的接口和扩展点。
2.  **使用AI生成实现**:\
    *   打开您喜欢的AI编程助手（例如，在VSCode中集成GitHub Copilot）。
    *   用自然语言描述您需要的功能，例如："创建一个实现`DataProcessorPlugin`接口的插件，该插件从一个文件中读取CSV数据，并将其存入数据库。"
    *   AI助手将为您生成代码的初稿。
3.  **审查和重构**: 对AI生成的代码进行审查、测试和重构，确保其符合项目规范和质量要求。
4.  **打包和部署**: 将完成的插件打包成JAR文件，并部署到AegisX框架中。

这种模式将核心框架的稳定性与业务插件的灵活性完美结合，同时开放地拥抱生态工具，共同缔造无与伦比的开发效率。

---

## 📞 社区与支持

### 🆘 获取帮助

#### 官方资源
- 📖 **完整文档**：[插件开发指南](docs/PLUGIN_DEVELOPMENT_GUIDE.md)
- 🎯 **快速示例**：参考 `database-demo-plugin` 项目
- 📋 **API 参考**：访问 http://localhost:8081/api-docs
- 🔍 **源码分析**：查看 `src/main/java/com/example/pf4j/` 目录

#### 社区支持
- 💬 **技术讨论**：[GitHub Discussions](https://github.com/a2443440243/AegisX/discussions)
- 🐛 **问题反馈**：[GitHub Issues](https://github.com/a2443440243/AegisX/issues)
- 📧 **商业支持**：enterprise@aegisx.dev
- 🤝 **技术交流群**：加入开发者社区

### 🤝 参与贡献

#### 贡献方式
- **代码贡献**：提交 Pull Request 改进框架
- **插件分享**：分享您开发的优秀插件
- **文档完善**：帮助改进文档和示例
- **问题反馈**：报告 Bug 和提出改进建议

#### 开发指南
1. Fork 项目到您的 GitHub 账户
2. 创建功能分支：`git checkout -b feature/your-feature`
3. 提交更改：`git commit -am 'Add some feature'`
4. 推送分支：`git push origin feature/your-feature`
5. 创建 Pull Request

### 📋 项目信息

- **项目名称**：PF4J Scaffold (AegisX)
- **当前版本**：1.0.0
- **技术栈**：Java 11 + Spring Boot + PF4J
- **许可证**：[MIT License](LICENSE)
- **维护状态**：🟢 积极维护中

### 🎯 发展路线图

#### 近期计划 (v1.1)
- [ ] 插件市场和在线安装
- [ ] 可视化插件开发工具
- [ ] 更多扩展点接口
- [ ] 性能监控和分析

#### 中期目标 (v2.0)
- [ ] 微服务插件支持
- [ ] 云原生部署优化
- [ ] AI 辅助插件开发
- [ ] 企业级安全增强

#### 长期愿景 (v3.0)
- [ ] 低代码插件开发平台
- [ ] 智能插件推荐系统
- [ ] 跨语言插件支持
- [ ] 边缘计算插件框架

### 💬 项目地址
- 🐙 **GitHub**: [https://github.com/a2443440243/AegisX](https://github.com/a2443440243/AegisX)
- 📦 **Gitee**: [https://gitee.com/cuixin_1/AegisX](https://gitee.com/cuixin_1/AegisX)

欢迎提交Issue和Pull Request来帮助改进项目！

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/NewFeature`)
3. 提交更改 (`git commit -m 'Add NewFeature'`)
4. 推送到分支 (`git push origin feature/NewFeature`)
5. 创建 Pull Request

---

## 📄 开源协议

本项目采用 [MIT License](LICENSE) 开源协议。

---

<div align="center">

**🚀 AegisX - 新一代企业级插件化架构 🚀**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PF4J](https://img.shields.io/badge/PF4J-3.9.0-blue.svg)](https://github.com/pf4j/pf4j)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

</div>
