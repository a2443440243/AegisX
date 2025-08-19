# 数据库操作示例插件

## 插件简介

这是一个基于PF4J框架的数据库操作示例插件，演示了如何在插件中使用框架提供的统一数据库操作功能。

## 功能特性

### 🔧 基础功能
- **CRUD操作**: 完整的用户增删改查功能
- **批量操作**: 支持批量保存、删除和更新
- **分页查询**: 支持分页和排序的数据查询
- **条件查询**: 支持多条件组合查询

### 📊 业务功能
- **用户验证**: 用户名密码验证功能
- **用户注册**: 新用户注册功能
- **权限检查**: 基础的用户权限验证
- **用户搜索**: 关键词搜索用户功能

### 📈 统计功能
- **用户统计**: 各种维度的用户数量统计
- **状态统计**: 按用户状态分类统计
- **角色统计**: 按用户角色分类统计

## 技术栈

- **PF4J**: 3.9.0 - 插件框架
- **Spring Boot**: 2.7.14 - 应用框架
- **Spring Data JPA**: 数据访问层
- **MySQL**: 8.0 - 数据库
- **Maven**: 项目构建工具

## 项目结构

```
database-demo-plugin/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/plugin/database/
│       │       ├── DatabaseDemoPlugin.java          # 插件主类
│       │       └── DatabaseDemoExtension.java       # 扩展实现类
│       └── resources/
│           └── plugin.properties                    # 插件配置文件
├── pom.xml                                         # Maven构建文件
└── README.md                                       # 项目说明文档
```

## 快速开始

### 1. 编译插件

```bash
# 进入插件目录
cd plugins/database-demo-plugin

# 编译打包
mvn clean package
```

### 2. 部署插件

将编译生成的JAR文件复制到框架的plugins目录：

```bash
# 复制插件文件
cp target/database-demo-plugin-1.0.0-jar-with-dependencies.jar ../../plugins/
```

### 3. 启动框架

启动主框架应用，插件会自动加载并运行。

## 使用示例

### 基础数据库操作

```java
// 创建用户
JavaAdmin user = DatabaseUtil.createUser("testuser", "password123", "test@example.com");

// 查询用户
Optional<JavaAdmin> foundUser = DatabaseUtil.findByUsername("testuser");

// 更新用户状态
boolean updated = DatabaseUtil.updateStatus(user.getId(), 1);

// 删除用户
DatabaseUtil.deleteById(user.getId());
```

### 业务查询操作

```java
// 根据邮箱查询
Optional<JavaAdmin> userByEmail = DatabaseUtil.findByEmail("test@example.com");

// 查询启用的用户
List<JavaAdmin> enabledUsers = DatabaseUtil.getEnabledUsers();

// 查询管理员用户
List<JavaAdmin> adminUsers = DatabaseUtil.getAdminUsers();

// 检查用户名是否存在
boolean exists = DatabaseUtil.existsByUsername("testuser");
```

### 统计操作

```java
// 统计总用户数
long totalCount = DatabaseUtil.count();

// 统计启用用户数
long enabledCount = DatabaseUtil.countByStatus(1);

// 获取用户统计信息
Map<String, Long> statistics = DatabaseUtil.getUserStatistics();
```

## 扩展开发

### 1. 实现DatabaseExtension接口

```java
@Extension
public class MyDatabaseExtension implements DatabaseExtension {
    // 实现接口方法
}
```

### 2. 使用DatabaseUtil工具类

```java
public class MyPlugin extends Plugin {
    public void someMethod() {
        // 直接使用工具类方法
        List<JavaAdmin> users = DatabaseUtil.findAll();
    }
}
```

## 配置说明

### plugin.properties

```properties
# 插件基本信息
plugin.id=database-demo-plugin
plugin.class=com.example.plugin.database.DatabaseDemoPlugin
plugin.version=1.0.0
plugin.provider=PF4J Framework

# 系统要求
plugin.requires=1.0.0

# 插件配置
plugin.enabled=true
plugin.autoStart=true
```

## 日志配置

插件使用SLF4J进行日志记录，支持以下日志级别：

- **DEBUG**: 详细的调试信息
- **INFO**: 一般信息记录
- **WARN**: 警告信息
- **ERROR**: 错误信息

## 测试

### 单元测试

```bash
# 运行测试
mvn test
```

### 集成测试

启动框架后，观察日志输出，确认插件正常加载和运行。

## 故障排除

### 常见问题

1. **插件加载失败**
   - 检查plugin.properties配置是否正确
   - 确认插件类路径是否正确
   - 查看框架日志获取详细错误信息

2. **数据库连接失败**
   - 检查数据库配置是否正确
   - 确认数据库服务是否正常运行
   - 验证数据库用户权限

3. **依赖冲突**
   - 检查Maven依赖版本是否兼容
   - 确认scope配置是否正确

### 调试技巧

1. **启用DEBUG日志**
   ```yaml
   logging:
     level:
       com.example.plugin.database: DEBUG
   ```

2. **查看插件状态**
   - 访问管理界面查看插件加载状态
   - 检查插件目录是否包含正确的JAR文件

## 版本历史

- **v1.0.0** (2024-01-20)
  - 初始版本发布
  - 实现基础CRUD操作
  - 添加业务查询功能
  - 支持统计操作

## 许可证

Apache License 2.0

## 联系方式

- **项目地址**: https://gitee.com/cuixin_1/pf4j-dynamic-plugin-framework
- **问题反馈**: 请在项目仓库中提交Issue
- **技术支持**: 查看框架文档或联系开发团队

## 贡献指南

1. Fork 项目仓库
2. 创建特性分支
3. 提交代码更改
4. 推送到分支
5. 创建 Pull Request

---

**注意**: 这是一个示例插件，主要用于演示如何使用框架的数据库操作功能。在生产环境中使用时，请根据实际需求进行适当的修改和优化。