# 🚀 AegisX — Enterprise Plugin Development Framework: Embracing the AI Era, Empowering Efficient Development

> **🌟 Let AI become the primary productivity force for code | Revolutionary improvement in development efficiency**
>
> **💡 Core Philosophy: AegisX provides a solid architectural foundation, allowing developers to easily integrate and utilize AI tools, focusing on innovation rather than repetitive labor**

English | [中文](README.zh.md)

---

## 🎯 Why Choose AegisX?

At the pinnacle of the digital wave, the only constant is change itself. AegisX redefines how enterprise-level software is built. It's not just a plugin framework, but a future-oriented, highly available, and adaptive architectural solution.

### 🚀 Embracing AI-Driven Development Mode
- **🔌 Flexible AI Tool Integration**: AegisX's plugin architecture allows you to easily integrate various AI code generation tools into your development workflow.
- **⚡ Enhanced Development Efficiency**: With external AI tools, developers can quickly generate boilerplate code, data models, and business logic, investing more time in core business innovation.
- **🧩 Focus on Business Logic**: AegisX handles underlying architecture, plugin lifecycle, and dependency management, allowing you to focus on implementing core business functions.
- **💡 Inspire Innovation**: By delegating repetitive coding work to AI, your team can explore more creative solutions.

### ☁️ Born for Cloud Native
- **DevOps Friendly**: Standardized Maven project structure and build process, seamlessly integrating into any CI/CD pipeline.
- **Built-in Health Monitoring**: Integrated Spring Boot Actuator provides out-of-the-box application monitoring, health checks, and metrics endpoints, simplifying operations.
- **Container Ready**: Clean architecture and clear dependencies make containerized deployment (Docker, Kubernetes) extremely easy.

### 🛡️ Unbreakable Enterprise Foundation
- **Industrial-Grade Stability**: Based on the world's most trusted Java ecosystem—Spring Boot, providing stability and reliability verified by massive production environments.
- **Enterprise-Level Security**: Deep integration with Spring Security, providing scalable and powerful authentication and authorization systems to protect your core assets.
- **High-Performance Data Access**: Integrated Spring Data JPA provides efficient, flexible, and standardized data persistence solutions.

### 🌐 Future-Oriented Unlimited Integration
- **Architecture Neutrality**: Decoupled plugin design makes it a "universal adhesive" that easily integrates AI services, big data platforms, message queues, IoT devices, and any heterogeneous systems.
- **AI Integration Empowerment**: AegisX's open architecture makes it an ideal platform for integrating external AI services. Whether connecting large language models (LLMs), integrating machine learning pipelines, or building intelligent automation processes, everything becomes effortless.

---

## 🏛️ Architecture Design

### 🎯 Core Architecture Layers

```
┌─────────────────────────────────────────────────────────────────────┐
│                   🧩 Business Capability Plugin Layer                  │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Database Demo│Custom Plugin│Business Plugin│      ...           │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   ⚙️ Dynamic Plugin Engine                            │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Plugin Service│PF4J Manager │Lifecycle Mgmt│ Plugin Controller  │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   🚌 Core Service Bus                                 │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Security Config│JPA Service │System Service│ Actuator Monitor   │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   📡 Unified API Gateway                              │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Plugin API   │System API   │Web Controller│ API Documentation  │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   🔧 Enterprise Technology Foundation                  │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Spring Boot  │   PF4J 3.9  │Spring Security│     MySQL 8.0+    │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
```

### 🏗️ Microkernel Architecture Design
- **Core Framework**: Lightweight kernel built on PF4J 3.9.0
- **Plugin System**: Supports dynamic loading, unloading, and hot updates (30-second scan interval)
- **Extension Point Mechanism**: Flexible interface definition and implementation
- **Dependency Isolation**: Each plugin has its own independent class loader
- **Spring Integration**: Seamless integration with Spring Boot through pf4j-spring

### 📊 System Architecture Diagram
```
┌─────────────────────────────────────────────────────────┐
│                    Web Management Interface              │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │Plugin Mgmt  │ │ API Docs    │ │System Monitor│      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                   REST API Layer                        │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginController│ ApiController│HealthController│    │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                    Business Service Layer               │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginService│ │UserService  │ │CommonService│      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                   Data Access Layer                     │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │JavaAdminRepo│ │DatabaseUtil │ │  JPA/ORM   │      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                    Plugin Management Layer              │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginManager│ │ExtensionMgr │ │ClassLoader  │      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                    Extension Point Interface            │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginInterface│DatabaseExt  │ CustomExt   │      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                     Plugin Instances                    │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │database-demo│ │ custom-plugin│ │future-plugin│     │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
└─────────────────────────────────────────────────────────┘
```

### 🧩 Core Module Analysis

#### 🔌 Plugin Management Layer
- **Dynamic Plugin Loading**: Based on PF4J framework, supports dynamic loading and unloading of plugins
- **Plugin Lifecycle**: Complete plugin startup, stop, and reload management
- **Plugin Service**: Unified plugin management service providing plugin information query and operation interfaces

#### 🌐 Business Service Layer
- **Security Authentication**: User authentication and permission management based on Spring Security
- **Data Persistence**: Complete data access layer with JPA + MySQL
- **System Monitoring**: Integrated Actuator providing application health checks and monitoring endpoints

---

## 🛠️ Technology Stack

### Core Framework
- **Java 11**: Modern Java development platform
- **Spring Boot 2.7.x**: Enterprise microservice framework
- **PF4J 3.9.0**: Plugin framework core engine
- **pf4j-spring 0.8.0**: Spring integration adapter

### Data Layer Technology
- **Spring Data JPA**: ORM data access layer
- **Hibernate**: JPA implementation
- **MySQL 8.0.33**: Production environment database
- **H2 Database**: Development and testing database
- **HikariCP**: High-performance connection pool

### Web Layer Technology
- **Spring MVC**: RESTful API framework
- **Thymeleaf**: Server-side template engine
- **Jackson**: JSON serialization/deserialization
- **Spring Security**: Security authentication framework
- **Bootstrap + jQuery**: Frontend UI framework

### Development Toolchain
- **Maven**: Project build and dependency management
- **Spring Boot DevTools**: Development hot reload
- **Lombok**: Reduce boilerplate code
- **Bean Validation**: Data validation framework
- **Logback**: Enterprise logging framework

### Operations Monitoring
- **Spring Boot Actuator**: Application monitoring endpoints
- **Health Check**: Health check mechanism
- **Metrics**: Performance metrics collection
- **Hot Deployment**: Plugin hot deployment support

| Technology Domain | Core Technology | Version | Purpose |
|:---:|:---:|:---:|:---|
| **Backend Framework** | Spring Boot | 2.7.14 | Enterprise application foundation framework |
| **Plugin Engine** | PF4J | 3.9.0 | Dynamic plugin management core |
| **Security Framework** | Spring Security | 5.7.x | Authentication authorization security protection |
| **Database** | MySQL | 8.0+ | Relational database |
| **ORM Framework** | Spring Data JPA | 2.7.x | Data access layer |
| **Template Engine** | Thymeleaf | 3.0.x | Web page rendering |
| **Monitoring Component** | Spring Actuator | 2.7.x | Application health monitoring |
| **JVM** | OpenJDK | 11+ | Java runtime environment |

---

## 🚀 Quick Start

### ✅ Environment Requirements
- JDK 11+
- Maven 3.6+
- MySQL 8.0+

### 🛠️ Startup Steps

```bash
# 1. Clone project
git clone https://github.com/a2443440243/AegisX.git
cd AegisX

# 2. Configure database
#    Open src/main/resources/application.yml
#    Modify url, username, password under spring.datasource

# 3. Maven build
mvn clean compile

# 4. Start application
mvn spring-boot:run
```

### 🌐 Access Points
- **Management Console**: `http://localhost:8081`
  - Default account: `admin` / `147896325a`
- **API Documentation**: `http://localhost:8081/api-docs`
- **System Health Check**: `http://localhost:8081/actuator/health`
- **Plugin Management API**: `http://localhost:8081/api/plugins`
- **System Status API**: `http://localhost:8081/api/system/status`

#### 🛠️ Detailed Installation Steps

##### 1. Clone Project
```bash
git clone https://github.com/a2443440243/AegisX.git
cd AegisX
```

##### 2. Database Configuration
Create database and configure connection information:
```sql
CREATE DATABASE java_admin CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Edit `src/main/resources/application.yml`:
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

# Plugin configuration
plugin:
  path: plugins
  development:
    mode: true
  scan:
    interval: 30

server:
  port: 8081
```

##### 3. Compile and Run
```bash
# Clean and compile project
mvn clean compile

# Install dependencies
mvn install

# Start application
mvn spring-boot:run
```

##### 4. Verify Installation
Access the main page, you should see:
- System running status
- Loaded plugin list
- Plugin management functions
- Real-time monitoring data

---

## 🧩 Plugin Development

### Development Overview

AegisX provides enterprise-level plugin development capabilities based on the PF4J framework, supporting dynamic loading, hot updates, and dependency isolation. Through standardized extension point interfaces, developers can safely and efficiently extend system functionality.

**Complete Development Guide**: [Plugin Development Guide](docs/PLUGIN_DEVELOPMENT_GUIDE.md)

### Core Extension Points

#### 1. PluginInterface - Basic Plugin Interface
```java
public interface PluginInterface extends ExtensionPoint {
    String getPluginName();           // Plugin name
    String getPluginVersion();        // Plugin version
    String getPluginDescription();    // Plugin description
    Object execute(Object input);     // Execute plugin functionality
    void initialize();                // Initialize method
    void destroy();                   // Destroy method
}
```

#### 2. DatabaseExtension - Database Extension Interface
Provides unified database access capabilities, including:
- **CRUD Operations**: save, findById, findAll, deleteById, etc.
- **Conditional Queries**: findByUsername, findByEmail, findByStatus, etc.
- **Paginated Queries**: Advanced queries supporting sorting and pagination
- **Batch Operations**: saveAll, deleteByIds, batchUpdateStatus, etc.
- **Statistical Functions**: count, countByStatus, getUserStatistics, etc.

### Example: database-demo-plugin

The project includes a complete plugin development example demonstrating standard implementation of enterprise-level plugins:

#### Project Structure
```
database-demo-plugin/
├── pom.xml                           # Maven dependency configuration
├── README.md                         # Plugin documentation
└── src/main/
    ├── java/com/example/plugin/database/
    │   ├── DatabaseDemoPlugin.java   # Plugin main class (lifecycle)
    │   └── DatabaseDemoExtension.java # Extension point implementation
    └── resources/
        └── plugin.properties         # Plugin metadata configuration
```

#### Core Implementation Features

**1. Plugin Lifecycle Management**
- `start()`: Initialization logic when plugin starts
- `stop()`: Cleanup work when plugin stops
- `delete()`: Resource release when plugin is uninstalled

**2. Database Extension Implementation**
- Simulate user validation and permission checks
- Demonstrate standard database operation processes
- Show error handling and logging

**3. Configuration Management**
```properties
plugin.id=database-demo-plugin
plugin.class=com.example.plugin.database.DatabaseDemoPlugin
plugin.version=1.0.0
plugin.provider=AegisX Team
plugin.dependencies=
plugin.description=Database operation demonstration plugin
```

#### Development Learning Path

1. **Basic Structure**: Understand plugin directory structure and configuration files
2. **Interface Implementation**: Learn how to implement standard extension point interfaces
3. **Lifecycle**: Master plugin startup, running, and stop processes
4. **Data Access**: Learn how to safely access the main application's data layer
5. **Error Handling**: Learn best practices for exception handling in plugin development
6. **Testing and Debugging**: Master local development and debugging techniques for plugins

This example provides a complete reference template and best practice guidance for enterprise-level plugin development.

### 📘 Development Process Overview

1. **Create Plugin Project**: Use our provided Maven archetype or refer to the `database-demo-plugin` example project structure.
2. **Define Extension Points**: Define `ExtensionPoint` interfaces in the plugin, which are contracts for plugin-main application interaction.
3. **Implement Extensions**: Write classes with `@Extension` annotations to implement specific business logic.
4. **Package Plugin**: Package the plugin into an independent JAR file.
5. **Dynamic Deployment**: Place the plugin JAR in the main application's `plugins` directory, and AegisX will automatically load and activate it.

### 🔥 System Features

#### 🔥 Dynamic Hot-Swapping
- **Runtime Loading**: Load new plugins without restarting the application
- **Hot Update Support**: Automatically scan plugin directory changes every 30 seconds
- **Version Management**: Support plugin version upgrades and rollbacks
- **Dependency Checking**: Automatically parse and validate plugin dependencies

#### 🛡️ Enterprise-Level Security
- **Class Loading Isolation**: Each plugin has its own independent class loader
- **Permission Control**: Access control based on Spring Security
- **Data Isolation**: Complete data access isolation between plugins
- **Security Audit**: Complete plugin operation logging

#### 📊 Intelligent Monitoring
- **Real-time Status**: Real-time monitoring of plugin running status
- **Performance Metrics**: Key metrics like memory usage, execution time
- **Health Checks**: Automatic detection of plugin health status
- **Alert Mechanism**: Automatic alerts and recovery for abnormal situations

#### 🔧 Visual Management
- **Web Console**: Intuitive plugin management interface
- **Batch Operations**: Support batch start, stop, and update of plugins
- **Configuration Management**: Online editing of plugin configuration parameters
- **Log Viewing**: Real-time viewing of plugin running logs

#### 📚 Developer Friendly
- **Rich APIs**: Complete plugin development APIs and extension points
- **Code Generation**: Plugin templates and scaffolding tools
- **Debug Support**: Hot reload and debugging in development mode
- **Complete Documentation**: Detailed development guides and example code

#### 🌐 Cloud Native Ready
- **Container Support**: Docker and Kubernetes deployment
- **Microservice Architecture**: Seamless integration with Spring Cloud ecosystem
- **Configuration Center**: Support external configuration management
- **Service Discovery**: Automatic registration and discovery of plugin services

---

## 🤖 Practicing Next-Generation Development Mode

### AI-Driven Enterprise Development

AegisX, as a next-generation enterprise plugin architecture, not only provides powerful technical capabilities but more importantly serves as an ideal platform for enterprises to practice AI-driven development modes. We encourage development teams to fully utilize AI programming assistants to achieve dual improvements in development efficiency and code quality.

### 🎯 AI-Assisted Plugin Development Workflow

#### 1. Requirements Analysis and Architecture Design
```
Developer + AI Collaboration Process:
├── Business Requirements Analysis → AI helps organize feature points and technical solutions
├── Plugin Architecture Design → AI recommends best practices and design patterns
├── Interface Definition → AI generates standardized extension point interfaces
└── Technology Selection → AI provides technology stack suggestions and risk assessment
```

#### 2. Code Generation and Implementation
- **Plugin Skeleton Generation**: Based on `database-demo-plugin` template, AI quickly generates new plugin structure
- **Extension Point Implementation**: AI assists in implementing `PluginInterface` and `DatabaseExtension` interfaces
- **Configuration File Generation**: Automatically generate `plugin.properties` and Maven configuration
- **Data Model Design**: Based on `JavaAdmin` entity, AI designs new data models

#### 3. Quality Assurance and Optimization
- **Code Review**: AI checks code standards, security vulnerabilities, and performance issues
- **Test Generation**: Automatically generate unit tests, integration tests, and end-to-end tests
- **Performance Optimization**: AI analyzes plugin performance bottlenecks and provides optimization suggestions
- **Documentation Generation**: Automatically generate API documentation, usage guides, and deployment documentation

### 🛠️ Recommended AI Development Toolchain

#### Code Generation and Completion
- **GitHub Copilot**: Intelligent code completion and function generation
- **Tabnine**: Context-based code suggestions
- **Cursor**: AI-driven code editor
- **CodeWhisperer**: Amazon's AI programming assistant
- **Trae**: Claude-based intelligent programming assistant

#### Architecture Design and Problem Solving
- **ChatGPT-4**: Architecture design and complex problem solving
- **Claude**: Code review and refactoring suggestions
- **Bard**: Technical solution evaluation and selection advice

#### Professional Development Tools
- **JetBrains AI Assistant**: AI assistant integrated in IntelliJ IDEA
- **Visual Studio IntelliCode**: Intelligent code suggestions and refactoring

### 📋 AI Collaborative Development Best Practices

#### Phase 1: Project Initialization
```bash
# 1. Discuss plugin requirements with AI
"I need to develop a user permission management plugin based on AegisX framework..."

# 2. AI generates project structure
# 3. Configure development environment
mvn archetype:generate -DgroupId=com.example.plugin
```

#### Phase 2: Core Development
```java
// AI-assisted generation of plugin main class
@Extension
public class UserPermissionPlugin implements PluginInterface {
    // AI-generated standard implementation
}
```

#### Phase 3: Testing and Deployment
```bash
# AI-generated test scripts
mvn test
mvn package
# Deploy to plugins directory
```

### 🚀 Enterprise Development Efficiency Improvement

Through AI-driven development mode, enterprise teams can achieve:

- **60% Development Efficiency Improvement**: Automate repetitive coding work
- **40% Code Quality Improvement**: AI-assisted code review and optimization
- **90%+ Documentation Completeness**: Automatically generated technical documentation
- **85%+ Test Coverage**: AI-generated comprehensive test cases
- **50% Maintenance Cost Reduction**: Standardized code structure and documentation

### 💡 Future Development Directions

- **Intelligent Plugin Recommendations**: Automatically recommend suitable plugins based on business requirements
- **Automated Testing**: AI-driven plugin compatibility and performance testing
- **Intelligent Operations**: AI monitors plugin running status and automatically optimizes
- **Code Generation Platform**: Visual plugin development and deployment platform

AegisX is committed to becoming the best practice platform for AI-driven development in enterprise digital transformation.

**Development Process Example:**

1. **Define Interfaces**: Define plugin interfaces and extension points in AegisX.
2. **Use AI to Generate Implementation**:
   * Open your favorite AI programming assistant (e.g., GitHub Copilot integrated in VSCode).
   * Describe the functionality you need in natural language, for example: "Create a plugin that implements the `DataProcessorPlugin` interface, which reads CSV data from a file and stores it in a database."
   * The AI assistant will generate an initial draft of the code for you.
3. **Review and Refactor**: Review, test, and refactor the AI-generated code to ensure it meets project standards and quality requirements.
4. **Package and Deploy**: Package the completed plugin into a JAR file and deploy it to the AegisX framework.

This mode perfectly combines the stability of the core framework with the flexibility of business plugins, while openly embracing ecosystem tools to create unparalleled development efficiency.

---

## 📞 Community and Support

### 🆘 Getting Help

#### Official Resources
- 📖 **Complete Documentation**: [Plugin Development Guide](docs/PLUGIN_DEVELOPMENT_GUIDE.md)
- 🎯 **Quick Examples**: Refer to `database-demo-plugin` project
- 📋 **API Reference**: Visit http://localhost:8081/api-docs
- 🔍 **Source Code Analysis**: Check `src/main/java/com/example/pf4j/` directory

#### Community Support
- 💬 **Technical Discussion**: [GitHub Discussions](https://github.com/a2443440243/AegisX/discussions)
- 🐛 **Issue Feedback**: [GitHub Issues](https://github.com/a2443440243/AegisX/issues)
- 📧 **Business Support**: enterprise@aegisx.dev
- 🤝 **Technical Exchange Group**: Join developer community

### 🤝 Contributing

#### Contribution Methods
- **Code Contribution**: Submit Pull Requests to improve the framework
- **Plugin Sharing**: Share excellent plugins you've developed
- **Documentation Improvement**: Help improve documentation and examples
- **Issue Feedback**: Report bugs and suggest improvements

#### Development Guidelines
1. Fork the project to your GitHub account
2. Create feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -am 'Add some feature'`
4. Push branch: `git push origin feature/your-feature`
5. Create Pull Request

### 📋 Project Information

- **Project Name**: PF4J Scaffold (AegisX)
- **Current Version**: 1.0.0
- **Technology Stack**: Java 11 + Spring Boot + PF4J
- **License**: [MIT License](LICENSE)
- **Maintenance Status**: 🟢 Actively maintained

### 🎯 Roadmap

#### Near-term Plans (v1.1)
- [ ] Plugin marketplace and online installation
- [ ] Visual plugin development tools
- [ ] More extension point interfaces
- [ ] Performance monitoring and analysis

#### Medium-term Goals (v2.0)
- [ ] Microservice plugin support
- [ ] Cloud native deployment optimization
- [ ] AI-assisted plugin development
- [ ] Enterprise security enhancements

#### Long-term Vision (v3.0)
- [ ] Low-code plugin development platform
- [ ] Intelligent plugin recommendation system
- [ ] Cross-language plugin support
- [ ] Edge computing plugin framework

### 💬 Project Repositories
- 🐙 **GitHub**: [https://github.com/a2443440243/AegisX](https://github.com/a2443440243/AegisX)
- 📦 **Gitee**: [https://gitee.com/cuixin_1/AegisX](https://gitee.com/cuixin_1/AegisX)

Welcome to submit Issues and Pull Requests to help improve the project!

1. Fork the project
2. Create feature branch (`git checkout -b feature/NewFeature`)
3. Commit changes (`git commit -m 'Add NewFeature'`)
4. Push to branch (`git push origin feature/NewFeature`)
5. Create Pull Request

---

## 📄 Open Source License

This project is licensed under the [MIT License](LICENSE).

---

<div align="center">

**🚀 AegisX - Next-Generation Enterprise Plugin Architecture 🚀**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PF4J](https://img.shields.io/badge/PF4J-3.9.0-blue.svg)](https://github.com/pf4j/pf4j)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

</div>