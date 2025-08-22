package com.example.plugin.database;

import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库演示扩展实现类
 * 实现DatabaseExtension接口，提供具体的数据库操作功能
 * 
 * @author AegisX Framework
 * @version 1.0.0
 */
@Extension
public class DatabaseDemoExtension implements DatabaseExtension {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseDemoExtension.class);
    
    // 模拟用户权限缓存
    private final Map<String, Map<String, Boolean>> userPermissions = new ConcurrentHashMap<>();
    
    // 扩展初始化状态
    private boolean initialized = false;
    
    /**
     * 获取扩展名称
     */
    @Override
    public String getExtensionName() {
        return "Database Demo Extension";
    }
    
    /**
     * 执行数据库操作
     */
    @Override
    public Object performDatabaseOperation(String operation, Object... params) {
        logger.info("执行数据库操作: {}, 参数: {}", operation, params);
        
        if (!initialized) {
            logger.warn("扩展尚未初始化，无法执行数据库操作");
            return null;
        }
        
        try {
            switch (operation.toLowerCase()) {
                case "query_users":
                    return queryUsers();
                case "query_user_by_id":
                    if (params.length > 0) {
                        return queryUserById(params[0]);
                    }
                    break;
                case "create_user":
                    if (params.length >= 2) {
                        return createUser((String) params[0], (String) params[1]);
                    }
                    break;
                case "update_user":
                    if (params.length >= 3) {
                        return updateUser(params[0], (String) params[1], (String) params[2]);
                    }
                    break;
                case "delete_user":
                    if (params.length > 0) {
                        return deleteUser(params[0]);
                    }
                    break;
                case "test_connection":
                    return testDatabaseConnection();
                default:
                    logger.warn("不支持的数据库操作: {}", operation);
                    return "不支持的操作: " + operation;
            }
        } catch (Exception e) {
            logger.error("执行数据库操作时发生错误: {}", operation, e);
            return "操作失败: " + e.getMessage();
        }
        
        return "操作完成";
    }
    
    /**
     * 验证用户权限
     */
    @Override
    public boolean validateUserPermission(String username, String operation) {
        logger.info("验证用户权限: 用户={}, 操作={}", username, operation);
        
        if (username == null || operation == null) {
            return false;
        }
        
        // 获取用户权限映射
        Map<String, Boolean> permissions = userPermissions.get(username);
        if (permissions == null) {
            // 为新用户初始化默认权限
            permissions = initializeDefaultPermissions();
            userPermissions.put(username, permissions);
        }
        
        // 检查具体操作权限
        Boolean hasPermission = permissions.get(operation.toLowerCase());
        boolean result = hasPermission != null && hasPermission;
        
        logger.info("用户 {} 对操作 {} 的权限验证结果: {}", username, operation, result);
        return result;
    }
    
    /**
     * 获取数据库连接状态
     */
    @Override
    public String getDatabaseConnectionStatus() {
        logger.info("检查数据库连接状态");
        
        try {
            // 模拟数据库连接检查
            Thread.sleep(100); // 模拟网络延迟
            
            Map<String, Object> status = new HashMap<>();
            status.put("connected", true);
            status.put("host", "192.168.100.221");
            status.put("database", "java_admin");
            status.put("username", "java_admin");
            status.put("connection_pool_size", 10);
            status.put("active_connections", 3);
            status.put("last_check_time", System.currentTimeMillis());
            
            return "数据库连接正常: " + status.toString();
        } catch (Exception e) {
            logger.error("检查数据库连接状态时发生错误", e);
            return "数据库连接异常: " + e.getMessage();
        }
    }
    
    /**
     * 初始化扩展
     */
    @Override
    public void initialize() {
        logger.info("=== 初始化数据库演示扩展 ===");
        
        try {
            // 初始化用户权限
            initializeUserPermissions();
            
            // 设置初始化状态
            initialized = true;
            
            logger.info("数据库演示扩展初始化成功！");
        } catch (Exception e) {
            logger.error("数据库演示扩展初始化失败", e);
            throw new RuntimeException("扩展初始化失败", e);
        }
    }
    
    /**
     * 销毁扩展
     */
    @Override
    public void destroy() {
        logger.info("=== 销毁数据库演示扩展 ===");
        
        try {
            // 清理资源
            userPermissions.clear();
            initialized = false;
            
            logger.info("数据库演示扩展销毁成功！");
        } catch (Exception e) {
            logger.error("数据库演示扩展销毁时发生错误", e);
        }
    }
    
    // ========== 私有辅助方法 ==========
    
    /**
     * 查询所有用户
     */
    private Object queryUsers() {
        logger.info("查询所有用户");
        // 模拟查询结果
        return "查询到 5 个用户: [admin, user1, user2, user3, guest]";
    }
    
    /**
     * 根据ID查询用户
     */
    private Object queryUserById(Object userId) {
        logger.info("根据ID查询用户: {}", userId);
        return "用户信息: {id=" + userId + ", name=用户" + userId + ", status=active}";
    }
    
    /**
     * 创建用户
     */
    private Object createUser(String username, String email) {
        logger.info("创建用户: username={}, email={}", username, email);
        return "用户创建成功: " + username;
    }
    
    /**
     * 更新用户
     */
    private Object updateUser(Object userId, String username, String email) {
        logger.info("更新用户: id={}, username={}, email={}", userId, username, email);
        return "用户更新成功: " + userId;
    }
    
    /**
     * 删除用户
     */
    private Object deleteUser(Object userId) {
        logger.info("删除用户: {}", userId);
        return "用户删除成功: " + userId;
    }
    
    /**
     * 测试数据库连接
     */
    private Object testDatabaseConnection() {
        logger.info("测试数据库连接");
        return getDatabaseConnectionStatus();
    }
    
    /**
     * 初始化用户权限
     */
    private void initializeUserPermissions() {
        logger.info("初始化用户权限配置");
        
        // 管理员权限
        Map<String, Boolean> adminPermissions = initializeDefaultPermissions();
        adminPermissions.put("delete_user", true);
        adminPermissions.put("create_user", true);
        adminPermissions.put("update_user", true);
        userPermissions.put("admin", adminPermissions);
        
        // 普通用户权限
        Map<String, Boolean> userPermissions = initializeDefaultPermissions();
        userPermissions.put("delete_user", false);
        userPermissions.put("create_user", false);
        this.userPermissions.put("user", userPermissions);
        
        // 访客权限
        Map<String, Boolean> guestPermissions = new HashMap<>();
        guestPermissions.put("query_users", true);
        guestPermissions.put("query_user_by_id", true);
        guestPermissions.put("create_user", false);
        guestPermissions.put("update_user", false);
        guestPermissions.put("delete_user", false);
        guestPermissions.put("test_connection", false);
        this.userPermissions.put("guest", guestPermissions);
        
        logger.info("用户权限配置初始化完成");
    }
    
    /**
     * 初始化默认权限
     */
    private Map<String, Boolean> initializeDefaultPermissions() {
        Map<String, Boolean> permissions = new HashMap<>();
        permissions.put("query_users", true);
        permissions.put("query_user_by_id", true);
        permissions.put("create_user", true);
        permissions.put("update_user", true);
        permissions.put("delete_user", false);
        permissions.put("test_connection", true);
        return permissions;
    }
}