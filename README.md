# PF4J 动态插件管理框架

基于 Spring Boot + PF4J + Spring Security 的企业级动态插件管理框架，支持插件热加载、安全认证、Web管理界面等功能。

## 项目特性

- 🔌 **动态插件加载**: 支持运行时加载、卸载和重新加载插件
- 🔐 **安全认证**: 集成 Spring Security，支持用户认证和权限控制
- 🏗️ **MVC 架构**: 清晰的分层架构，易于维护和扩展
- 🌐 **RESTful API**: 完整的插件管理 REST 接口
- 📊 **Web 管理界面**: 直观的插件管理 Web 界面
- 🔧 **开发友好**: 支持开发模式，便于插件开发和调试
- 📈 **监控支持**: 集成 Spring Boot Actuator 健康检查

## 技术栈

- **Spring Boot 2.7.14**: 应用框架
- **PF4J 3.9.0**: 插件框架
- **Spring Security 5.7.2**: 安全框架
- **Thymeleaf**: 模板引擎
- **Maven**: 项目管理
- **Jackson**: JSON 处理
- **SLF4J + Logback**: 日志管理

## 项目结构

```
pf4j-scaffold/
├── src/main/java/com/example/pf4j/
│   ├── config/
│   │   └── PluginManagerConfig.java     # 插件管理器配置
│   ├── controller/
│   │   ├── PluginController.java        # 插件管理控制器
│   │   └── SystemController.java        # 系统状态控制器
│   ├── service/
│   │   └── PluginService.java           # 插件业务逻辑服务
│   ├── plugin/
│   │   ├── PluginInterface.java         # 插件接口定义
│   │   ├── HelloWorldPlugin.java        # 示例插件1
│   │   └── DataProcessorPlugin.java     # 示例插件2
│   └── Pf4jScaffoldApplication.java     # 应用启动类
├── src/main/resources/
│   ├── application.yml                  # 应用配置
│   └── static/
│       └── index.html                   # Web 管理界面
├── plugins/                             # 插件目录
├── pom.xml                              # Maven 配置
└── README.md                            # 项目说明
```

## 快速开始

### 1. 环境要求

- JDK 8+
- Maven 3.6+

### 2. 启动应用

```bash
# 编译项目
mvn clean compile

# 启动应用
mvn spring-boot:run
```

### 3. 访问应用

- **Web 管理界面**: http://localhost:8080
- **健康检查**: http://localhost:8080/actuator/health
- **应用信息**: http://localhost:8080/actuator/info

## API 接口

### 插件管理 API

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/plugins` | 获取所有插件列表 |
| GET | `/api/plugins/{name}` | 获取指定插件详情 |
| POST | `/api/plugins/{name}/execute` | 执行指定插件 |
| POST | `/api/plugins/{name}/start` | 启动指定插件 |
| POST | `/api/plugins/{name}/stop` | 停止指定插件 |
| POST | `/api/plugins/reload` | 重新加载所有插件 |

### 系统管理 API

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/system/status` | 获取系统状态 |
| GET | `/api/system/health` | 系统健康检查 |
| GET | `/api/system/info` | 获取系统信息 |
| POST | `/api/system/restart` | 重启系统 |

## 插件开发

### 1. 创建插件项目

创建一个新的 Maven 项目，添加以下依赖：

```xml
<dependency>
    <groupId>org.pf4j</groupId>
    <artifactId>pf4j</artifactId>
    <version>3.10.0</version>
    <scope>provided</scope>
</dependency>
```

### 2. 实现插件接口

```java
@Extension
public class MyPlugin implements PluginInterface {
    
    @Override
    public String getName() {
        return "MyPlugin";
    }
    
    @Override
    public String getVersion() {
        return "1.0.0";
    }
    
    @Override
    public String getDescription() {
        return "我的自定义插件";
    }
    
    @Override
    public Object execute(Object input) {
        // 插件业务逻辑
        return "执行结果";
    }
}
```

### 3. 创建插件描述文件

在 `src/main/resources/META-INF` 目录下创建 `extensions.idx` 文件：

```
com.example.MyPlugin
```

### 4. 打包插件

```bash
mvn clean package
```

### 5. 部署插件

将生成的 JAR 文件复制到应用的 `plugins` 目录下，然后调用重新加载 API。

## 配置说明

### application.yml 配置项

```yaml
# 服务器配置
server:
  port: 8080

# 插件配置
pf4j:
  plugin-path: plugins          # 插件目录路径
  development-mode: true        # 开发模式

# 日志配置
logging:
  level:
    com.example.pf4j: DEBUG
    org.pf4j: INFO
```

## 开发模式

开发模式下的特性：

- 支持从源码目录加载插件
- 自动检测插件变更
- 详细的调试日志
- 热重载支持

## 生产部署

### 1. 构建生产包

```bash
mvn clean package -Pprod
```

### 2. 配置生产环境

修改 `application.yml`：

```yaml
pf4j:
  development-mode: false

logging:
  level:
    root: WARN
    com.example.pf4j: INFO
```

### 3. 启动应用

```bash
java -jar target/pf4j-scaffold-1.0.0.jar
```

## 监控和运维

### 健康检查

```bash
curl http://localhost:8080/actuator/health
```

### 查看插件状态

```bash
curl http://localhost:8080/api/plugins
```

### 重新加载插件

```bash
curl -X POST http://localhost:8080/api/plugins/reload
```

## 故障排除

### 常见问题

1. **插件加载失败**
   - 检查插件 JAR 文件是否在正确的目录
   - 验证插件的 `extensions.idx` 文件
   - 查看应用日志中的错误信息

2. **插件执行异常**
   - 检查插件的依赖是否完整
   - 验证插件接口实现是否正确
   - 查看插件的日志输出

3. **Web 界面无法访问**
   - 确认应用已正常启动
   - 检查端口是否被占用
   - 验证防火墙设置

### 日志配置

增加调试日志：

```yaml
logging:
  level:
    org.pf4j: DEBUG
    com.example.pf4j: DEBUG
```

## 扩展开发

### 自定义插件接口

可以根据业务需求扩展插件接口：

```java
public interface CustomPluginInterface extends ExtensionPoint {
    void customMethod();
}
```

### 插件生命周期管理

实现插件的初始化和销毁逻辑：

```java
@Override
public void initialize() {
    // 插件初始化逻辑
}

@Override
public void destroy() {
    // 插件销毁逻辑
}
```

## 许可证

MIT License

## 贡献

欢迎提交 Issue 和 Pull Request！

## 联系方式

如有问题，请通过以下方式联系：

- 邮箱: example@example.com
- Gitee: https://gitee.com/cuixin_1/pf4j-dynamic-plugin-framework
