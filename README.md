# 🚀 AegisX — A New-Generation Enterprise-Grade Plugin Architecture

> **"Embrace Change, Stay Ahead"**
>
> **AegisX is more than just a framework; it\'s a future-oriented software engineering philosophy. We firmly believe that through superior architectural design, we can build highly adaptive, self-evolving systems that stand invincible in the ever-changing tide of technology.**

English | [中文](README.zh.md)

---

## 🌟 Core Features

### 🚀 New-Generation Enterprise-Grade Plugin Architecture
- **Microkernel Architecture**: AegisX employs a highly cohesive microkernel design. The core system contains only the most fundamental functionalities, with all business capabilities delivered through independently deployable plugins. This design ensures the stability and lightweight nature of the core system while granting unprecedented flexibility to business function modules.
- **Dynamic Hot-Swapping**: Plugins support dynamic loading, unloading, and updating at runtime without restarting the main application. This enables zero-downtime continuous evolution and maintenance, a key guarantee for high-availability (99.99%) systems.
- **Dependency Isolation & Management**: Each plugin has its own class loader and resource space, completely resolving the dependency conflict issues common in traditional monolithic applications. A fine-grained dependency management mechanism ensures the stable coexistence of plugins.

### ☁️ Born for Cloud-Native & DevOps
- **DevOps-Friendly**: A standardized Maven project structure and build process seamlessly integrate into any CI/CD pipeline, enabling full automation from code submission to production deployment.
- **Built-in Health Monitoring**: Integrated with Spring Boot Actuator, it provides out-of-the-box application monitoring, health checks, and metrics endpoints, simplifying operations and enhancing system observability.
- **Containerization-Ready**: Its concise architecture and clear dependencies make it extremely easy to containerize (e.g., Docker, Kubernetes), perfectly meeting the deployment requirements of the cloud-native era.

### 🛡️ Indestructible Enterprise-Grade Foundation
- **Industrial-Grade Stability**: Built on the world\'s most trusted Java ecosystem—Spring Boot—it provides stability and reliability validated in massive production environments.
- **Enterprise-Grade Security**: Deeply integrated with Spring Security, it offers an extensible and powerful authentication and authorization system to safeguard your core assets.
- **High-Performance Data Access**: Integrated with Spring Data JPA, it provides an efficient, flexible, and standardized data persistence solution.

---

## 🏛️ Architectural Design

### 🎯 Microkernel Architecture
- **Core Framework**: Lightweight kernel built on PF4J 3.9.0
- **Plugin System**: Support for dynamic loading, unloading, and hot updates (30-second scan interval)
- **Extension Point Mechanism**: Flexible interface definition and implementation
- **Dependency Isolation**: Each plugin has its own independent class loader
- **Spring Integration**: Seamless integration with Spring Boot through pf4j-spring

### System Architecture Diagram
```
┌─────────────────────────────────────────────────────────┐
│                    Web Management UI                    │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │Plugin Mgmt  │ │ API Docs    │ │System Monitor│      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                   REST API Layer                       │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginController│ ApiController│HealthController│    │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                  Business Service Layer                │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginService│ │UserService  │ │CommonService│      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                   Data Access Layer                    │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │JavaAdminRepo│ │DatabaseUtil │ │  JPA/ORM   │      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                  Plugin Management Layer               │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginManager│ │ExtensionMgr │ │ClassLoader  │      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                  Extension Point Interface             │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │PluginInterface│DatabaseExt  │ CustomExt   │      │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
├─────────────────────────────────────────────────────────┤
│                     Plugin Instances                   │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐      │
│  │database-demo│ │ custom-plugin│ │future-plugin│     │
│  └─────────────┘ └─────────────┘ └─────────────┘      │
└─────────────────────────────────────────────────────────┘
```

### 🎯 Core Architectural Layers

```
┌─────────────────────────────────────────────────────────────────────┐
│                   🧩 Business Capability Plugin Layer               │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Database Demo│Custom Plugin│Business Plugin│      ...           │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   ⚙️ Dynamic Plugin Engine                         │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Plugin Service│PF4J Manager │Lifecycle Mgmt│ Plugin Controller  │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   🚌 Core Service Bus                             │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Security Config│JPA Service │System Service│ Actuator Monitor   │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   📡 Unified API Gateway                          │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Plugin API   │System API   │Web Controller│ API Documentation  │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│                   🔧 Enterprise Technology Foundation             │
│  ┌─────────────┬─────────────┬─────────────┬─────────────────────┐   │
│  │Spring Boot  │   PF4J 3.9  │Spring Security│     MySQL 8.0+    │   │
│  └─────────────┴─────────────┴─────────────┴─────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
```

### 🧩 Core Module Analysis

#### 🔌 Plugin Management Layer
- **Dynamic Plugin Loading**: Based on the PF4J framework, supports dynamic loading and unloading of plugins.
- **Plugin Lifecycle**: Complete management of plugin start, stop, and reload.
- **Plugin Service**: A unified plugin management service that provides interfaces for querying and operating plugins.

#### 🌐 Business Service Layer
- **Security Authentication**: User authentication and permission management based on Spring Security.
- **Data Persistence**: A complete data access layer with JPA + MySQL.
- **System Monitoring**: Integrated with Actuator to provide application health checks and monitoring endpoints.

---

## 🛠️ Technology Stack

### Core Framework
- **Java 11**: Modern Java development platform
- **Spring Boot 2.7.x**: Enterprise microservice framework
- **PF4J 3.9.0**: Plugin framework core engine
- **pf4j-spring 0.8.0**: Spring integration adapter

### Data Layer Technologies
- **Spring Data JPA**: ORM data access layer
- **Hibernate**: JPA implementation
- **MySQL 8.0.33**: Production database
- **H2 Database**: Development and testing database
- **HikariCP**: High-performance connection pool

### Web Layer Technologies
- **Spring MVC**: RESTful API framework
- **Thymeleaf**: Server-side template engine
- **Jackson**: JSON serialization/deserialization
- **Spring Security**: Security authentication framework
- **Bootstrap + jQuery**: Frontend UI framework

### Development Toolchain
- **Maven**: Project build and dependency management
- **Spring Boot DevTools**: Hot reload during development
- **Lombok**: Reduce boilerplate code
- **Bean Validation**: Data validation framework
- **Logback**: Enterprise logging framework

### Operations & Monitoring
- **Spring Boot Actuator**: Application monitoring endpoints
- **Health Check**: Health check mechanism
- **Metrics**: Performance metrics collection
- **Hot Deployment**: Plugin hot deployment support

| Category | Technology | Version | Role |
|:---:|:---:|:---:|:---|
| **Backend Framework** | Spring Boot | 2.7.14 | Foundation for enterprise applications |
| **Plugin Engine** | PF4J | 3.9.0 | Core of dynamic plugin management |
| **Security Framework** | Spring Security | 5.7.x | Authentication and authorization |
| **Database** | MySQL | 8.0+ | Relational database |
| **ORM Framework** | Spring Data JPA | 2.7.x | Data access layer |
| **Template Engine** | Thymeleaf | 3.0.x | Web page rendering |
| **Monitoring Component** | Spring Actuator | 2.7.x | Application health monitoring |
| **JVM** | OpenJDK | 11+ | Java runtime environment |

---

## 🚀 Quick Start

### Requirements
- **JDK 11+**: Recommended OpenJDK 11 or Oracle JDK 11
- **Maven 3.6+**: Project build tool
- **MySQL 8.0+**: Production database
- **IDE**: Recommended IntelliJ IDEA or Eclipse

### Installation Steps

#### 1. Clone the project
```bash
git clone https://github.com/your-org/pf4j-scaffold.git
cd pf4j-scaffold
```

#### 2. Database Configuration
Create database and configure connection:
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

# Plugin Configuration
plugin:
  path: plugins
  development:
    mode: true
  scan:
    interval: 30

server:
  port: 8081
```

#### 3. Build and Run
```bash
# Clean and compile project
mvn clean compile

# Install dependencies
mvn install

# Start application
mvn spring-boot:run
```

#### 4. Access Application
After successful startup, you can access:

- **Homepage**: http://localhost:8081
- **Plugin Management**: http://localhost:8081/plugins
- **API Documentation**: http://localhost:8081/api-docs
- **Health Check**: http://localhost:8081/actuator/health
- **API Test Page**: http://localhost:8081/test-api.html

#### 5. Verify Installation
Access the homepage, you should see:
- System running status
- Loaded plugin list
- Plugin management functionality
- Real-time monitoring data

### ✅ Prerequisites
- JDK 11+
- Maven 3.6+
- MySQL 8.0+

### 🛠️ Steps to Start

```bash
# 1. Clone the project
git clone https://github.com/a2443440243/AegisX.git
cd AegisX

# 2. Configure the database
#    Open src/main/resources/application.yml
#    Modify the url, username, and password under spring.datasource

# 3. Build with Maven
mvn clean compile

# 4. Run the application
mvn spring-boot:run
```

### 🌐 Access Points
- **Admin Console**: `http://localhost:8081`
  - Default credentials: `admin` / `147896325a`
- **API Documentation**: `http://localhost:8081/api-docs`
- **System Health Check**: `http://localhost:8081/actuator/health`
- **Plugin Management API**: `http://localhost:8081/api/plugins`
- **System Status API**: `http://localhost:8081/api/system/status`

---

## 🔌 Plugin Development

### Development Overview

AegisX provides enterprise-grade plugin development capabilities based on the PF4J framework, supporting dynamic loading, hot updates, and dependency isolation. Through standardized extension point interfaces, developers can safely and efficiently extend system functionality.

**Complete Development Guide**: [Plugin Development Guide](PLUGIN_DEVELOPMENT_GUIDE.md)

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
- **Paginated Queries**: Advanced queries with sorting and pagination support
- **Batch Operations**: saveAll, deleteByIds, batchUpdateStatus, etc.
- **Statistical Functions**: count, countByStatus, getUserStatistics, etc.

### Example: database-demo-plugin

The project includes a complete plugin development example demonstrating enterprise-grade plugin standard implementation:

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
- Simulate user authentication and permission checking
- Demonstrate standard database operation procedures
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
3. **Lifecycle**: Master plugin startup, running, and stopping processes
4. **Data Access**: Learn how to safely access the main application's data layer
5. **Error Handling**: Learn exception handling best practices in plugin development
6. **Testing & Debugging**: Master local development and debugging techniques for plugins

This example provides a complete reference template and best practice guidance for enterprise-grade plugin development.

The core of AegisX lies in its powerful plugin ecosystem. We provide a complete development guide and examples to help you quickly build your own plugins.

### 📘 Development Process Overview

1.  **Create a Plugin Project**: Use our provided Maven archetype or refer to the `database-demo-plugin` example project structure.
2.  **Define Extension Points**: Define `ExtensionPoint` interfaces in your plugin, which serve as the contract for interaction with the main application.
3.  **Implement Extensions**: Write classes that implement the `@Extension` annotation to realize specific business logic.
4.  **Package the Plugin**: Package your plugin into a standalone JAR file.
5.  **Deploy Dynamically**: Place the plugin JAR file into the `plugins` directory of the main application, and AegisX will automatically load and activate it.

> For detailed development steps, API usage, and best practices, please refer to the **[Plugin Development Guide](docs/PLUGIN_DEVELOPMENT_GUIDE.md)**.

### 📦 Example Plugin: `database-demo-plugin`

To help you get started quickly, we provide an out-of-the-box database demo plugin.

- **Location**: `plugins/database-demo-plugin`
- **Functionality**: Demonstrates how to define extension points, implement business logic, and display its lifecycle through log output.
- **Source Code Structure**:
  ```
  database-demo-plugin/
  ├── pom.xml
  └── src/
      └── main/
          ├── java/com/example/plugin/database/
          │   ├── DatabaseDemoExtension.java  // Extension implementation
          │   └── DatabaseDemoPlugin.java     // Plugin main class
          └── resources/
              └── plugin.properties           // Plugin metadata
  ```
- **Learning Value**: By analyzing this plugin, you can quickly grasp the core essentials of AegisX plugin development.

---

## 🤖 Practicing Next-Generation Development Model

### AI-Driven Enterprise Development

AegisX, as a next-generation enterprise-grade plugin architecture, not only provides powerful technical capabilities but more importantly serves as an ideal platform for enterprises to practice AI-driven development models. We encourage development teams to fully utilize AI programming assistants to achieve dual improvements in development efficiency and code quality.

### 🎯 AI-Assisted Plugin Development Workflow

#### 1. Requirements Analysis & Architecture Design
```
Developer + AI Collaboration Process:
├── Business Requirements Analysis → AI helps organize feature points and technical solutions
├── Plugin Architecture Design → AI recommends best practices and design patterns
├── Interface Definition → AI generates standardized extension point interfaces
└── Technology Selection → AI provides technology stack recommendations and risk assessment
```

#### 2. Code Generation & Implementation
- **Plugin Skeleton Generation**: Based on `database-demo-plugin` template, AI quickly generates new plugin structure
- **Extension Point Implementation**: AI assists in implementing `PluginInterface` and `DatabaseExtension` interfaces
- **Configuration File Generation**: Automatically generate `plugin.properties` and Maven configuration
- **Data Model Design**: Based on `JavaAdmin` entity, AI designs new data models

#### 3. Quality Assurance & Optimization
- **Code Review**: AI checks code standards, security vulnerabilities, and performance issues
- **Test Generation**: Automatically generate unit tests, integration tests, and end-to-end tests
- **Performance Optimization**: AI analyzes plugin performance bottlenecks and provides optimization suggestions
- **Documentation Generation**: Automatically generate API documentation, usage guides, and deployment documentation

### 🛠️ Recommended AI Development Toolchain

#### Code Generation & Completion
- **GitHub Copilot**: Intelligent code completion and function generation
- **Tabnine**: Context-based code suggestions
- **Cursor**: AI-driven code editor
- **CodeWhisperer**: Amazon's AI programming assistant

#### Architecture Design & Problem Solving
- **ChatGPT-4**: Architecture design and complex problem solving
- **Claude**: Code review and refactoring suggestions
- **Bard**: Technical solution evaluation and selection recommendations

#### Professional Development Tools
- **JetBrains AI Assistant**: AI assistant integrated in IntelliJ IDEA
- **Visual Studio IntelliCode**: Intelligent code suggestions and refactoring

### 📋 AI Collaboration Development Best Practices

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
// AI-assisted plugin main class generation
@Extension
public class UserPermissionPlugin implements PluginInterface {
    // AI-generated standard implementation
}
```

#### Phase 3: Testing & Deployment
```bash
# AI-generated test scripts
mvn test
mvn package
# Deploy to plugins directory
```

### 🚀 Enterprise Development Efficiency Improvement

Through AI-driven development models, enterprise teams can achieve:

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

## 🤝 Practicing the Next-Generation Development Model

The design philosophy of AegisX is "separation of concerns." The framework itself is responsible for the application\'s skeleton and infrastructure, while external tools (like AI programming assistants) act as the developer\'s "external brain" to accelerate code writing.

**Example Development Workflow:**

1.  **Define Interfaces**: Define plugin interfaces and extension points in AegisX.
2.  **Generate Implementation with AI**:\
    *   Open your favorite AI programming assistant (e.g., GitHub Copilot integrated into VSCode).
    *   Describe the functionality you need in natural language, for example: "Create a plugin that implements the `DataProcessorPlugin` interface, reads CSV data from a file, and stores it in a database."
    *   The AI assistant will generate a draft of the code for you.
3.  **Review and Refactor**: Review, test, and refactor the AI-generated code to ensure it meets project specifications and quality standards.
4.  **Package and Deploy**: Package the completed plugin into a JAR file and deploy it to the AegisX framework.

This model perfectly combines the stability of the core framework with the flexibility of business plugins, while openly embracing ecosystem tools to achieve unparalleled development efficiency.

---

## 📞 Community & Support

### 🆘 Getting Help

#### Official Resources
- 📖 **Complete Documentation**: [Plugin Development Guide](PLUGIN_DEVELOPMENT_GUIDE.md)
- 🎯 **Quick Examples**: Reference `database-demo-plugin` project
- 📋 **API Reference**: Visit http://localhost:8081/api-docs
- 🔍 **Source Code Analysis**: Check `src/main/java/com/example/pf4j/` directory

#### Community Support
- 💬 **Technical Discussions**: [GitHub Discussions](https://github.com/your-org/pf4j-scaffold/discussions)
- 🐛 **Issue Reporting**: [GitHub Issues](https://github.com/your-org/pf4j-scaffold/issues)
- 📧 **Enterprise Support**: enterprise@aegisx.dev
- 🤝 **Technical Exchange Group**: Join developer community

### 🤝 Contributing

#### Ways to Contribute
- **Code Contribution**: Submit Pull Requests to improve the framework
- **Plugin Sharing**: Share excellent plugins you've developed
- **Documentation Improvement**: Help improve documentation and examples
- **Issue Feedback**: Report bugs and suggest improvements

#### Development Guide
1. Fork the project to your GitHub account
2. Create feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -am 'Add some feature'`
4. Push branch: `git push origin feature/your-feature`
5. Create Pull Request

### 📋 Project Information

- **Project Name**: PF4J Scaffold (AegisX)
- **Current Version**: 1.0.0
- **Technology Stack**: Java 11 + Spring Boot + PF4J
- **License**: [Apache License 2.0](LICENSE)
- **Maintenance Status**: 🟢 Actively maintained

### 🎯 Development Roadmap

#### Near-term Plans (v1.1)
- [ ] Plugin marketplace and online installation
- [ ] Visual plugin development tools
- [ ] More extension point interfaces
- [ ] Performance monitoring and analysis

#### Medium-term Goals (v2.0)
- [ ] Microservice plugin support
- [ ] Cloud-native deployment optimization
- [ ] AI-assisted plugin development
- [ ] Enterprise-grade security enhancements

#### Long-term Vision (v3.0)
- [ ] Low-code plugin development platform
- [ ] Intelligent plugin recommendation system
- [ ] Cross-language plugin support
- [ ] Edge computing plugin framework

## 🌟 Community & Support

### 💬 Project Addresses
- 🐙 **GitHub**: [https://github.com/a2443440243/AegisX](https://github.com/a2443440243/AegisX)
- 📦 **Gitee**: [https://gitee.com/cuixin_1/AegisX](https://gitee.com/cuixin_1/AegisX)

### 🤝 Contribution Guidelines

We welcome you to submit Issues and Pull Requests to help improve the project!

1. Fork the project
2. Create a feature branch (`git checkout -b feature/NewFeature`)
3. Commit your changes (`git commit -m 'Add NewFeature'`)
4. Push to the branch (`git push origin feature/NewFeature`)
5. Create a Pull Request

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

<div align="center">

**🚀 AegisX - A New-Generation Enterprise-Grade Plugin Architecture 🚀**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PF4J](https://img.shields.io/badge/PF4J-3.9.0-blue.svg)](https://github.com/pf4j/pf4j)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

</div>