package com.example.plugin.database;

import org.pf4j.ExtensionPoint;

/**
 * 数据库扩展接口
 * 定义了插件可以实现的数据库操作扩展点
 * 
 * @author AegisX Framework
 * @version 1.0.0
 */
public interface DatabaseExtension extends ExtensionPoint {
    
    /**
     * 获取扩展名称
     * @return 扩展名称
     */
    String getExtensionName();
    
    /**
     * 执行数据库操作
     * @param operation 操作类型
     * @param params 操作参数
     * @return 操作结果
     */
    Object performDatabaseOperation(String operation, Object... params);
    
    /**
     * 验证用户权限
     * @param username 用户名
     * @param operation 操作类型
     * @return 是否有权限
     */
    boolean validateUserPermission(String username, String operation);
    
    /**
     * 获取数据库连接状态
     * @return 连接状态信息
     */
    String getDatabaseConnectionStatus();
    
    /**
     * 初始化扩展
     */
    void initialize();
    
    /**
     * 销毁扩展，释放资源
     */
    void destroy();
}