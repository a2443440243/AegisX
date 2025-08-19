package com.example.plugin.database;

import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库扩展接口
 */
interface DatabaseExtension extends ExtensionPoint {
    String getExtensionName();
    void performDatabaseOperation();
}

/**
 * 数据库操作演示扩展实现
 * 
 * 这是一个简化的数据库扩展示例，展示了如何创建PF4J扩展
 * 在实际应用中，可以通过依赖注入获取数据库服务来执行真实的数据库操作
 */
@Extension
public class DatabaseDemoExtension implements DatabaseExtension {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseDemoExtension.class);
    
    @Override
    public String getExtensionName() {
        return "Database Demo Extension";
    }
    
    @Override
    public void performDatabaseOperation() {
        logger.info("执行数据库操作演示");
        logger.info("扩展名称: {}", getExtensionName());
        logger.info("这是一个数据库操作扩展的示例实现");
    }
    
    /**
     * 获取扩展信息
     * 
     * @return 扩展信息
     */
    public String getExtensionInfo() {
        return "Database Demo Extension v1.0.0 - 提供数据库操作示例";
    }
    
    /**
     * 执行示例操作
     */
    public void executeDemo() {
        logger.info("=== 数据库扩展演示 ===");
        logger.info("扩展已成功加载并可以执行数据库相关操作");
        logger.info("在实际应用中，这里可以实现具体的数据库CRUD操作");
    }
    
    /**
     * 模拟用户验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 验证结果
     */
    public boolean validateUser(String username, String password) {
        logger.info("模拟用户验证: {}", username);
        // 简化的验证逻辑
        return "admin".equals(username) && "password".equals(password);
    }
    
    /**
     * 模拟权限检查
     * 
     * @param userId 用户ID
     * @param permission 权限
     * @return 权限检查结果
     */
    public boolean hasPermission(String userId, String permission) {
        logger.info("模拟权限检查: userId={}, permission={}", userId, permission);
        // 简化的权限检查逻辑
        return "admin".equals(userId) || "read".equals(permission);
    }
}