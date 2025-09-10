package com.example.plugin.useradmin;

import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 用户管理扩展实现
 * 提供用户增删改查功能
 */
@Extension
public class UserAdminExtension implements ExtensionPoint {
    private static final Logger logger = LoggerFactory.getLogger(UserAdminExtension.class);
    
    // 模拟用户数据存储
    private final Map<String, Map<String, Object>> users = new HashMap<>();
    
    public UserAdminExtension() {
        // 初始化一些示例用户数据
        initSampleUsers();
    }
    
    /**
     * 获取插件名称
     */
    public String getPluginName() {
        return "用户管理插件";
    }
    
    /**
     * 获取插件版本
     */
    public String getPluginVersion() {
        return "1.0.0";
    }
    
    /**
     * 获取插件描述
     */
    public String getPluginDescription() {
        return "提供用户增删改查功能的管理插件，支持用户信息的完整生命周期管理";
    }
    
    /**
     * 执行用户管理操作
     */
    public Object execute(Object input) {
        logger.info("执行用户管理操作，输入参数: {}", input);
        
        if (input instanceof Map) {
            Map<String, Object> params = (Map<String, Object>) input;
            String action = (String) params.get("action");
            
            switch (action) {
                case "list":
                    return listUsers();
                case "create":
                    return createUser(params);
                case "update":
                    return updateUser(params);
                case "delete":
                    return deleteUser(params);
                case "get":
                    return getUser(params);
                default:
                    return Map.of("error", "不支持的操作: " + action);
            }
        }
        
        // 默认返回用户列表
        return listUsers();
    }
    
    /**
     * 插件初始化方法
     */
    public void initialize() {
        logger.info("用户管理插件扩展初始化完成");
        logger.info("当前用户数量: {}", users.size());
    }
    
    /**
     * 插件销毁方法
     */
    public void destroy() {
        logger.info("用户管理插件扩展销毁完成");
        users.clear();
    }
    
    /**
     * 初始化示例用户数据
     */
    private void initSampleUsers() {
        Map<String, Object> admin = new HashMap<>();
        admin.put("id", "1");
        admin.put("username", "admin");
        admin.put("email", "admin@example.com");
        admin.put("role", "管理员");
        admin.put("status", "ACTIVE");
        admin.put("createTime", new Date());
        users.put("1", admin);
        
        Map<String, Object> user = new HashMap<>();
        user.put("id", "2");
        user.put("username", "user");
        user.put("email", "user@example.com");
        user.put("role", "普通用户");
        user.put("status", "ACTIVE");
        user.put("createTime", new Date());
        users.put("2", user);
        
        logger.info("初始化了 {} 个示例用户", users.size());
    }
    
    /**
     * 获取用户列表
     */
    private Map<String, Object> listUsers() {
        logger.info("获取用户列表，当前用户数量: {}", users.size());
        return Map.of(
            "success", true,
            "data", new ArrayList<>(users.values()),
            "total", users.size()
        );
    }
    
    /**
     * 创建用户
     */
    private Map<String, Object> createUser(Map<String, Object> params) {
        String username = (String) params.get("username");
        String email = (String) params.get("email");
        String role = (String) params.get("role");
        
        if (username == null || username.trim().isEmpty()) {
            return Map.of("success", false, "message", "用户名不能为空");
        }
        
        // 检查用户名是否已存在
        for (Map<String, Object> user : users.values()) {
            if (username.equals(user.get("username"))) {
                return Map.of("success", false, "message", "用户名已存在");
            }
        }
        
        String id = String.valueOf(users.size() + 1);
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("id", id);
        newUser.put("username", username);
        newUser.put("email", email != null ? email : "");
        newUser.put("role", role != null ? role : "普通用户");
        newUser.put("status", "ACTIVE");
        newUser.put("createTime", new Date());
        
        users.put(id, newUser);
        logger.info("创建用户成功: {}", username);
        
        return Map.of("success", true, "message", "用户创建成功", "data", newUser);
    }
    
    /**
     * 更新用户
     */
    private Map<String, Object> updateUser(Map<String, Object> params) {
        String id = (String) params.get("id");
        if (id == null || !users.containsKey(id)) {
            return Map.of("success", false, "message", "用户不存在");
        }
        
        Map<String, Object> user = users.get(id);
        if (params.containsKey("email")) {
            user.put("email", params.get("email"));
        }
        if (params.containsKey("role")) {
            user.put("role", params.get("role"));
        }
        if (params.containsKey("status")) {
            user.put("status", params.get("status"));
        }
        user.put("updateTime", new Date());
        
        logger.info("更新用户成功: {}", user.get("username"));
        return Map.of("success", true, "message", "用户更新成功", "data", user);
    }
    
    /**
     * 删除用户
     */
    private Map<String, Object> deleteUser(Map<String, Object> params) {
        String id = (String) params.get("id");
        if (id == null || !users.containsKey(id)) {
            return Map.of("success", false, "message", "用户不存在");
        }
        
        Map<String, Object> user = users.remove(id);
        logger.info("删除用户成功: {}", user.get("username"));
        
        return Map.of("success", true, "message", "用户删除成功");
    }
    
    /**
     * 获取单个用户
     */
    private Map<String, Object> getUser(Map<String, Object> params) {
        String id = (String) params.get("id");
        if (id == null || !users.containsKey(id)) {
            return Map.of("success", false, "message", "用户不存在");
        }
        
        Map<String, Object> user = users.get(id);
        return Map.of("success", true, "data", user);
    }
}