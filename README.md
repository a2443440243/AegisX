# AegisX - Next-Generation Enterprise Plugin-based Architecture

<div align="center">

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PF4J](https://img.shields.io/badge/PF4J-3.9.0-blue.svg)](https://github.com/pf4j/pf4j)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Actively%20Maintained-green.svg)](#)

**AegisX (PF4J Scaffold) is an intelligent, plugin-based enterprise application scaffold built on PF4J and Spring Boot, designed for next-generation development.**

</div>

AegisX aims to provide enterprises with a highly extensible, secure, and reliable modern application platform that is deeply integrated with the AI-driven development model. It not only inherits the lightweight and high-performance nature of PF4J but also incorporates the powerful ecosystem of Spring Boot, offering developers an unparalleled development experience and efficiency.

---

## Table of Contents

- [✨ Core Features](#-core-features)
- [🚀 Quick Start](#-quick-start)
- [🛠️ Architecture Design](#️-architecture-design)
- [🧩 Plugin Development](#-plugin-development)
- [🤖 AI-Driven Development](#-ai-driven-development)
- [🤝 Community & Contribution](#-community--contribution)
- [📄 Project Information](#-project-information)

---

## ✨ Core Features

AegisX offers a range of enterprise-grade features designed to enhance development efficiency, system stability, and security.

| Feature | Description |
| :--- | :--- |
| **🔥 Dynamic Hot-Swapping** | Dynamically load, update, and unload plugins without restarting the application, enabling agile iteration of business functions. |
| **🛡️ Enterprise-Grade Security** | Each plugin has its own classloader for resource isolation. Combined with Spring Security, it provides fine-grained permission control. |
| **📊 Intelligent Monitoring** | Real-time monitoring of plugin status and performance metrics (memory, CPU), with health checks and automatic alerting. |
| **🔧 Visual Management** | An intuitive web console supports batch start/stop, configuration management, and log viewing for plugins. |
| **📚 Developer-Friendly** | Rich APIs, code generation tools, and comprehensive documentation help developers get started quickly. |
| **🌐 Cloud-Native Ready** | Supports containerized deployment (Docker/Kubernetes) and seamless integration with microservices ecosystems (e.g., Spring Cloud). |
| **🤖 AI-Driven** | Deeply integrates AI-assisted development, empowering the entire process from requirement design to code generation, testing, and documentation. |

---
## 🚀 Quick Start

### Prerequisites
- **Java**: 11 or higher
- **Maven**: 3.6 or higher
- **MySQL**: 5.7 or higher

### Installation & Running

**1. Clone the Project**
```bash
git clone https://github.com/a2443440243/AegisX.git
cd AegisX
```

**2. Database Configuration**
First, create a database in your MySQL instance.
```sql
CREATE DATABASE java_admin CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
Then, edit the `src/main/resources/application.yml` file to update your database connection information.
```yaml
spring:
  datasource:
    url: jdbc:mysql://192.168.100.221:3306/java_admin # <-- Change to your database address
    username: java_admin # <-- Change to your username
    password: your_password # <-- Change to your password
```

**3. Compile and Run**
```bash
# Clean, compile, and install dependencies
mvn clean install

# Start the application
mvn spring-boot:run
```

**4. Access the System**
Once the application is running, you can access it at the following addresses:
- **Admin Console**: `http://localhost:8081` (Default account: `admin` / `147896325a`)
- **API Documentation**: `http://localhost:8081/api-docs`
- **Health Check**: `http://localhost:8081/actuator/health`

---

## 🛠️ Architecture Design

AegisX employs an advanced microkernel architecture combined with a layered design philosophy to ensure high cohesion, low coupling, and high extensibility.

### Core Concepts
- **Microkernel**: The core system remains lightweight, providing only the most basic services (plugin management, lifecycle, extension points). All business functions are implemented as plugins.
- **Layered Architecture**: A clear hierarchical structure (Core, Interface, Plugin, Application layers) ensures separation of concerns and maintainability.
- **AI-Driven**: Deeply integrates AI toolchains into the development workflow to empower developers and improve efficiency and quality.

### System Architecture Diagram

![AegisX System Architecture Diagram](docs/AegisX-Architecture.png)

*(Diagram: Shows the relationship between the core system, plugin manager, extension point interfaces, security module, monitoring module, and various plugins.)*

### Technology Stack

| Technology | Purpose |
| :--- | :--- |
| **Core Framework** | Spring Boot 2.7.14, PF4J 3.9.0 |
| **Data Persistence** | Spring Data JPA, Hibernate, MySQL |
| **Security Control** | Spring Security |
| **API Documentation** | SpringDoc (OpenAPI 3) |
| **Monitoring** | Spring Boot Actuator |
| **Build Tool** | Maven |

---
## 🧩 Plugin Development

Plugin development in AegisX follows PF4J standards while providing richer enterprise-level support.

**Full Development Guide**: [Plugin Development Deep Dive](docs/PLUGIN_DEVELOPMENT_GUIDE.md)

### Core Extension Points

AegisX provides two core extension point interfaces that plugins implement to interact with the main system.

**1. `PluginInterface` - Base Plugin Interface**
All plugins must implement this base interface, which defines the plugin's metadata and core execution logic.
```java
public interface PluginInterface extends ExtensionPoint {
    String getPluginName();
    String getPluginVersion();
    Object execute(Object input);
    // ... other lifecycle methods
}
```

**2. `DatabaseExtension` - Database Extension Interface**
Provides a secure and standardized data access API for plugins that need to interact with the main application's database.
```java
public interface DatabaseExtension extends ExtensionPoint {
    // Provides CRUD, conditional queries, pagination, batch operations, etc.
    <T> T save(T entity);
    <T> Optional<T> findById(Class<T> entityClass, Long id);
    // ... more data operation methods
}
```

### Development Workflow Overview

1.  **Create Plugin Project**: Use the provided Maven archetype or refer to the `database-demo-plugin` example.
2.  **Implement Extension Points**: Write classes that implement `PluginInterface` or `DatabaseExtension`.
3.  **Add `@Extension` Annotation**: Add the `@Extension` annotation to your implementation class so PF4J can discover it.
4.  **Package and Deploy**: Package the plugin as a JAR file and place it in the main application's `plugins` directory. AegisX will automatically load and activate it.

### Example: `database-demo-plugin`

The project includes a fully functional database operation plugin example (`database-demo-plugin`) that demonstrates best practices for enterprise plugin development. We highly recommend studying this example before you start developing.

---

## 🤖 AI-Driven Development

AegisX is not just a framework but a practice platform for an advanced development model. We encourage teams to fully embrace AI to elevate development efficiency to new heights.

### AI-Assisted Development Workflow

We recommend a development process that combines human intelligence with AI efficiency:

1.  **Requirements & Design (Human + AI)**:
    *   **Developer**: Proposes business requirements.
    *   **AI**: Assists in analyzing requirements, suggesting technical solutions, and designing plugin architecture and interfaces.

2.  **Code Generation (AI)**:
    *   **AI**: Quickly generates plugin skeletons, implements extension points, and writes configuration files and data models based on the design.

3.  **Review & Optimization (Human + AI)**:
    *   **Developer**: Reviews the AI-generated code, fine-tuning business logic and performing refactoring.
    *   **AI**: Assists with code quality checks, identifying potential bugs, and suggesting optimizations.

4.  **Testing & Documentation (AI)**:
    *   **AI**: Automatically generates unit tests, integration test cases, and standard API documentation and user manuals.

### Recommended AI Toolchain

- **Code Generation**: GitHub Copilot, Tabnine, Cursor
- **Architecture & Consulting**: ChatGPT-4, Claude, Trae
- **IDE Integration**: JetBrains AI Assistant, VS IntelliCode

With this model, development teams can free themselves from repetitive work to focus on business innovation and solving complex problems, achieving a **several-fold increase in development efficiency**.

---
## 🤝 Community & Contribution

We welcome all forms of contributions, whether it's code, documentation, or issue feedback.

- **Issue Tracker**: [GitHub Issues](https://github.com/a2443440243/AegisX/issues)
- **Discussions**: [GitHub Discussions](https://github.com/a2443440243/AegisX/discussions)
- **Contributing**: Please refer to our [Contribution Guide](CONTRIBUTING.md).

### Project Addresses
- 🐙 **GitHub**: [https://github.com/a2443440243/AegisX](https://github.com/a2443440243/AegisX)
- 📦 **Gitee**: [https://gitee.com/cuixin_1/AegisX](https://gitee.com/cuixin_1/AegisX)

---

## 📄 Project Information

### Development Roadmap

**Short-Term Plan (v1.1)**
- [ ] Plugin marketplace and online installation
- [ ] Visual plugin development tools
- [ ] Enhanced performance monitoring dashboard

**Long-Term Vision (v3.0)**
- [ ] Low-code/no-code plugin generation platform
- [ ] AI-driven intelligent operations and self-healing capabilities
- [ ] Cross-language plugin support (e.g., Python, Go)

### License
This project is licensed under the [MIT License](LICENSE).

---

<div align="center">
**🚀 AegisX - Empowering Enterprises, Embracing the Future 🚀**
</div>