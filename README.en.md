# 🚀 AegisX — Enterprise Dynamic Plugin Core Framework

> **Motto: No Tech Debt — Eliminate Legacy Code. Simplicity is Power.**

[中文文档](README.md) | English

AegisX is not a collection of templates and boilerplate code, but a lightweight plugin kernel forged for **actual production environments**. It unifies "extensibility", "security", "observability" and "developer experience" in an extremely simple and clear architecture.

---

# Why Choose AegisX

- ✅ **Zero Redundancy**: Keep the core minimal, eliminate all meaningless dependencies and boilerplate.
- ✅ **Eliminate Legacy Code**: Convention over configuration, unified code standards and extension points, reduce arbitrariness.
- ✅ **Hot-swappable Plugins**: Load/unload/update plugins at runtime without service restart, support source code loading and jar deployment.
- ✅ **Enterprise Security**: Built-in Spring Security authorization and authentication templates, extensible permission granularity.
- ✅ **Observability**: Actuator + custom monitoring points, business visualization monitoring.
- ✅ **Developer Friendly**: Minimal templates, example plugins, automated scaffolding, get started in 5 minutes.

---

# Highlights Overview

- Dynamic plugin management (load / unload / restart / query)
- Unified security and authentication model
- Lightweight REST API and management interface
- Plugin-level logging, monitoring, isolation
- Clear module layering (core / api / plugin / web)
- Complete database integration and operation services

---

# AI-Friendly & Modern Development

- **No Tech Debt — Eliminate Legacy Code**: We have zero tolerance for technical debt, code quality is a hard threshold.
- **AI Pair Programmer**: Collaborate with LLM programming, provide intelligent completion, suggestions and refactoring.
- **Prompt Engineering**: Good prompt = good code, AegisX supports prompt-driven development workflow.
- **LLM-assisted Refactoring**: Use models to provide refactoring suggestions, improve code cleanliness and consistency.
- **Self-hosted LLM**: Support access to enterprise self-hosted models, ensure privacy and compliance.

# Tech Stack

- Spring Boot 2.x
- PF4J (Plugin Kernel)
- Spring Security (Authentication)
- Spring Data JPA + MySQL (Persistence)
- Thymeleaf (Management Interface)
- Actuator (Health Check)
- Maven (Build)

## Database Integration

The framework provides comprehensive database integration capabilities, supporting dynamic data source configuration and plugin-level database operations.

### Configuration

```yaml
# Database configuration
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/plugin_db?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
```

### Entity Example

```java
@Entity
@Table(name = "demo_entity")
public class DemoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    // Getters and Setters
}
```

### Repository Interface

The framework provides the `JavaAdmin` repository interface for business queries:

```java
public interface JavaAdmin<T, ID> extends JpaRepository<T, ID> {
    
    /**
     * Find entities by name (fuzzy search)
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.name LIKE %:name%")
    List<T> findByNameContaining(@Param("name") String name);
    
    /**
     * Find entities by creation time range
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.createdTime BETWEEN :startTime AND :endTime")
    List<T> findByCreatedTimeBetween(@Param("startTime") LocalDateTime startTime, 
                                   @Param("endTime") LocalDateTime endTime);
    
    /**
     * Paginated query with sorting
     */
    @Query("SELECT e FROM #{#entityName} e ORDER BY e.createdTime DESC")
    Page<T> findAllOrderByCreatedTimeDesc(Pageable pageable);
    
    /**
     * Count entities by status
     */
    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.status = :status")
    long countByStatus(@Param("status") String status);
    
    /**
     * Get statistics data
     */
    @Query("SELECT new map(e.status as status, COUNT(e) as count) FROM #{#entityName} e GROUP BY e.status")
    List<Map<String, Object>> getStatusStatistics();
}
```

### Plugin Database Extension

Plugins can extend database functionality through the `DatabaseExtension` interface:

```java
@Extension
public class DemoPluginDatabaseExtension implements DatabaseExtension {
    
    @Override
    public void initializeDatabase() {
        // Initialize plugin-specific database tables
        DatabaseUtil.executeUpdate(
            "CREATE TABLE IF NOT EXISTS plugin_data (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "plugin_id VARCHAR(255), " +
            "data_key VARCHAR(255), " +
            "data_value TEXT, " +
            "created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")"
        );
    }
    
    @Override
    public void cleanupDatabase() {
        // Clean up plugin data when unloading
        DatabaseUtil.executeUpdate("DELETE FROM plugin_data WHERE plugin_id = ?", getPluginId());
    }
}
```

### Database Utility Class

The framework provides the `DatabaseUtil` class for convenient database operations:

```java
// Query operations
List<Map<String, Object>> results = DatabaseUtil.queryForList(
    "SELECT * FROM demo_entity WHERE name LIKE ?", 
    "%demo%"
);

// Update operations
int affectedRows = DatabaseUtil.executeUpdate(
    "UPDATE demo_entity SET description = ? WHERE id = ?", 
    "Updated description", 1L
);

// Batch operations
List<Object[]> batchArgs = Arrays.asList(
    new Object[]{"Name1", "Desc1"},
    new Object[]{"Name2", "Desc2"}
);
DatabaseUtil.batchUpdate(
    "INSERT INTO demo_entity (name, description) VALUES (?, ?)", 
    batchArgs
);

// Transaction operations
DatabaseUtil.executeInTransaction(() -> {
    DatabaseUtil.executeUpdate("INSERT INTO demo_entity (name) VALUES (?)", "Test");
    DatabaseUtil.executeUpdate("UPDATE demo_entity SET description = ? WHERE name = ?", "Updated", "Test");
    return null;
});
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── framework/
│   │               ├── FrameworkApplication.java
│   │               ├── config/          # Configuration classes
│   │               ├── controller/      # REST controllers
│   │               ├── service/         # Business services
│   │               ├── repository/      # Data access layer
│   │               ├── entity/          # JPA entities
│   │               ├── plugin/          # Plugin management
│   │               └── security/        # Security configuration
│   └── resources/
│       ├── application.yml              # Main configuration
│       ├── templates/                   # Thymeleaf templates
│       └── static/                      # Static resources
└── plugins/
    └── database-demo-plugin/            # Example plugin
```

---

# Quick Start (Shortest Runnable Path)

```bash
# Compile
mvn clean package

# Start in development mode
mvn spring-boot:run
```

- Management Console: `http://localhost:8080`
- Health Check: `http://localhost:8080/actuator/health`
- Hot Reload Plugins: `POST http://localhost:8080/api/plugins/reload`

## API Interfaces

### Plugin Management

```bash
# Get all plugins
GET /api/plugins

# Load plugin
POST /api/plugins/load/{pluginId}

# Unload plugin
POST /api/plugins/unload/{pluginId}

# Reload plugin
POST /api/plugins/reload/{pluginId}

# Get plugin details
GET /api/plugins/{pluginId}
```

### System Management

```bash
# System status
GET /api/system/status

# Health check
GET /actuator/health

# Application info
GET /actuator/info
```

## Plugin Development

### 1. Create Plugin Project

```xml
<dependency>
    <groupId>org.pf4j</groupId>
    <artifactId>pf4j</artifactId>
    <version>3.9.0</version>
    <scope>provided</scope>
</dependency>
```

### 2. Implement Plugin Class

```java
@Extension
public class DemoPlugin extends Plugin {
    
    public DemoPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    @Override
    public void start() {
        System.out.println("Demo Plugin started!");
    }
    
    @Override
    public void stop() {
        System.out.println("Demo Plugin stopped!");
    }
}
```

### 3. Plugin Configuration

```properties
# plugin.properties
plugin.id=demo-plugin
plugin.class=com.example.DemoPlugin
plugin.version=1.0.0
plugin.provider=Example Corp
plugin.dependencies=
```

## Configuration

### Development Mode

```yaml
pf4j:
  mode: development
  pluginsDir: plugins
  systemVersion: 1.0.0
```

### Production Mode

```yaml
pf4j:
  mode: deployment
  pluginsDir: /opt/app/plugins
  systemVersion: 1.0.0
```

## Monitoring and Operations

### Health Check

```bash
curl http://localhost:8080/actuator/health
```

### Plugin Status Monitoring

```bash
curl http://localhost:8080/api/plugins/status
```

### Log Configuration

```yaml
logging:
  level:
    com.example.framework: DEBUG
    org.pf4j: INFO
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
```

## Troubleshooting

### Common Issues

1. **Plugin loading failure**
   - Check plugin.properties configuration
   - Verify plugin dependencies
   - Check log output

2. **Database connection issues**
   - Verify database configuration
   - Check network connectivity
   - Confirm database permissions

3. **Permission issues**
   - Check Spring Security configuration
   - Verify user roles and permissions
   - Review authentication settings

## Extension Development

The framework supports multiple extension points:

- **Plugin Extensions**: Extend plugin functionality
- **Security Extensions**: Custom authentication and authorization
- **Database Extensions**: Custom data access logic
- **Web Extensions**: Custom web interfaces

---

# License

MIT License — Free to use, just keep the copyright notice.

# Contribution Guidelines

We welcome all forms of contributions!

- 🐛 **Bug Reports**: Please submit Issues if you find problems
- 💡 **Feature Suggestions**: Share your great ideas
- 🔧 **Code Contributions**: Submit PRs to improve the project
- 📖 **Documentation Improvements**: Help improve documentation

**Contribution Principle**: No Tech Debt — Eliminate legacy code, maintain code quality

---

# Contact

📧 **Email**: cenzui1314520@gmail.com

If you have any questions or suggestions, feel free to contact us via email!
