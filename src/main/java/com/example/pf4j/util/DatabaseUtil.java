package com.example.pf4j.util;

import com.example.pf4j.entity.JavaAdmin;
import com.example.pf4j.extension.DatabaseExtension;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据库操作工具类
 * 为插件提供便捷的数据库访问方法
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
public class DatabaseUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);
    
    private static PluginManager pluginManager;
    private static DatabaseExtension databaseExtension;
    
    /**
     * 初始化数据库工具类
     * 
     * @param manager 插件管理器
     */
    public static void initialize(PluginManager manager) {
        pluginManager = manager;
        // 获取数据库扩展实例
        List<DatabaseExtension> extensions = pluginManager.getExtensions(DatabaseExtension.class);
        if (!extensions.isEmpty()) {
            databaseExtension = extensions.get(0);
            logger.info("数据库工具类初始化成功");
        } else {
            logger.error("未找到数据库扩展实现，数据库工具类初始化失败");
        }
    }
    
    /**
     * 检查数据库扩展是否可用
     */
    private static void checkExtension() {
        if (databaseExtension == null) {
            throw new RuntimeException("数据库扩展未初始化，请先调用 DatabaseUtil.initialize() 方法");
        }
    }
    
    // ==================== 基础CRUD操作 ====================
    
    /**
     * 保存用户
     * 
     * @param javaAdmin 用户实体
     * @return 保存后的用户实体
     */
    public static JavaAdmin save(JavaAdmin javaAdmin) {
        checkExtension();
        return databaseExtension.save(javaAdmin);
    }
    
    /**
     * 批量保存用户
     * 
     * @param javaAdmins 用户列表
     * @return 保存后的用户列表
     */
    public static List<JavaAdmin> saveAll(List<JavaAdmin> javaAdmins) {
        checkExtension();
        return databaseExtension.saveAll(javaAdmins);
    }
    
    /**
     * 根据ID查找用户
     * 
     * @param id 用户ID
     * @return 用户实体（可能为空）
     */
    public static Optional<JavaAdmin> findById(Long id) {
        checkExtension();
        return databaseExtension.findById(id);
    }
    
    /**
     * 根据用户名查找用户
     * 
     * @param username 用户名
     * @return 用户实体（可能为空）
     */
    public static Optional<JavaAdmin> findByUsername(String username) {
        checkExtension();
        return databaseExtension.findByUsername(username);
    }
    
    /**
     * 查找所有用户
     * 
     * @return 用户列表
     */
    public static List<JavaAdmin> findAll() {
        checkExtension();
        return databaseExtension.findAll();
    }
    
    /**
     * 分页查询用户
     * 
     * @param page 页码（从0开始）
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向（ASC/DESC）
     * @return 分页结果
     */
    public static Page<JavaAdmin> findAll(int page, int size, String sortBy, String sortDir) {
        checkExtension();
        return databaseExtension.findAll(page, size, sortBy, sortDir);
    }
    
    /**
     * 删除用户
     * 
     * @param id 用户ID
     */
    public static void deleteById(Long id) {
        checkExtension();
        databaseExtension.deleteById(id);
    }
    
    /**
     * 批量删除用户
     * 
     * @param ids 用户ID列表
     */
    public static void deleteByIds(List<Long> ids) {
        checkExtension();
        databaseExtension.deleteByIds(ids);
    }
    
    // ==================== 业务查询方法 ====================
    
    /**
     * 根据邮箱查找用户
     * 
     * @param email 邮箱
     * @return 用户实体（可能为空）
     */
    public static Optional<JavaAdmin> findByEmail(String email) {
        checkExtension();
        return databaseExtension.findByEmail(email);
    }
    
    /**
     * 根据手机号查找用户
     * 
     * @param phone 手机号
     * @return 用户实体（可能为空）
     */
    public static Optional<JavaAdmin> findByPhone(String phone) {
        checkExtension();
        return databaseExtension.findByPhone(phone);
    }
    
    /**
     * 根据状态查找用户列表
     * 
     * @param status 状态
     * @return 用户列表
     */
    public static List<JavaAdmin> findByStatus(Integer status) {
        checkExtension();
        return databaseExtension.findByStatus(status);
    }
    
    /**
     * 根据角色查找用户列表
     * 
     * @param role 角色
     * @return 用户列表
     */
    public static List<JavaAdmin> findByRole(String role) {
        checkExtension();
        return databaseExtension.findByRole(role);
    }
    
    /**
     * 多条件查询用户
     * 
     * @param username 用户名（可选）
     * @param email 邮箱（可选）
     * @param status 状态（可选）
     * @param role 角色（可选）
     * @param page 页码
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向
     * @return 分页结果
     */
    public static Page<JavaAdmin> findByConditions(String username, String email, Integer status, String role,
                                                  int page, int size, String sortBy, String sortDir) {
        checkExtension();
        return databaseExtension.findByConditions(username, email, status, role, page, size, sortBy, sortDir);
    }
    
    // ==================== 业务操作方法 ====================
    
    /**
     * 检查用户名是否存在
     * 
     * @param username 用户名
     * @return 是否存在
     */
    public static boolean existsByUsername(String username) {
        checkExtension();
        return databaseExtension.existsByUsername(username);
    }
    
    /**
     * 检查邮箱是否存在
     * 
     * @param email 邮箱
     * @return 是否存在
     */
    public static boolean existsByEmail(String email) {
        checkExtension();
        return databaseExtension.existsByEmail(email);
    }
    
    /**
     * 更新用户状态
     * 
     * @param id 用户ID
     * @param status 新状态
     * @return 是否更新成功
     */
    public static boolean updateStatus(Long id, Integer status) {
        checkExtension();
        return databaseExtension.updateStatus(id, status);
    }
    
    /**
     * 更新用户密码
     * 
     * @param id 用户ID
     * @param password 新密码
     * @return 是否更新成功
     */
    public static boolean updatePassword(Long id, String password) {
        checkExtension();
        return databaseExtension.updatePassword(id, password);
    }
    
    /**
     * 批量更新用户状态
     * 
     * @param ids 用户ID列表
     * @param status 新状态
     * @return 更新的记录数
     */
    public static int batchUpdateStatus(List<Long> ids, Integer status) {
        checkExtension();
        return databaseExtension.batchUpdateStatus(ids, status);
    }
    
    // ==================== 统计方法 ====================
    
    /**
     * 统计总用户数
     * 
     * @return 用户总数
     */
    public static long count() {
        checkExtension();
        return databaseExtension.count();
    }
    
    /**
     * 统计指定状态的用户数量
     * 
     * @param status 状态
     * @return 用户数量
     */
    public static long countByStatus(Integer status) {
        checkExtension();
        return databaseExtension.countByStatus(status);
    }
    
    /**
     * 统计指定角色的用户数量
     * 
     * @param role 角色
     * @return 用户数量
     */
    public static long countByRole(String role) {
        checkExtension();
        return databaseExtension.countByRole(role);
    }
    
    /**
     * 获取用户统计信息
     * 
     * @return 统计信息Map
     */
    public static Map<String, Long> getUserStatistics() {
        checkExtension();
        return databaseExtension.getUserStatistics();
    }
    
    // ==================== 便捷方法 ====================
    
    /**
     * 创建新用户
     * 
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return 创建的用户实体
     */
    public static JavaAdmin createUser(String username, String password, String email) {
        checkExtension();
        JavaAdmin user = new JavaAdmin(username, password, email);
        return databaseExtension.save(user);
    }
    
    /**
     * 创建管理员用户
     * 
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return 创建的管理员用户实体
     */
    public static JavaAdmin createAdmin(String username, String password, String email) {
        checkExtension();
        JavaAdmin admin = new JavaAdmin(username, password, email);
        admin.setRole("admin");
        return databaseExtension.save(admin);
    }
    
    /**
     * 启用用户
     * 
     * @param id 用户ID
     * @return 是否操作成功
     */
    public static boolean enableUser(Long id) {
        checkExtension();
        return databaseExtension.updateStatus(id, 1);
    }
    
    /**
     * 禁用用户
     * 
     * @param id 用户ID
     * @return 是否操作成功
     */
    public static boolean disableUser(Long id) {
        checkExtension();
        return databaseExtension.updateStatus(id, 0);
    }
    
    /**
     * 获取启用的用户列表
     * 
     * @return 启用的用户列表
     */
    public static List<JavaAdmin> getEnabledUsers() {
        checkExtension();
        return databaseExtension.findByStatus(1);
    }
    
    /**
     * 获取禁用的用户列表
     * 
     * @return 禁用的用户列表
     */
    public static List<JavaAdmin> getDisabledUsers() {
        checkExtension();
        return databaseExtension.findByStatus(0);
    }
    
    /**
     * 获取管理员用户列表
     * 
     * @return 管理员用户列表
     */
    public static List<JavaAdmin> getAdminUsers() {
        checkExtension();
        return databaseExtension.findByRole("admin");
    }
    
    /**
     * 获取普通用户列表
     * 
     * @return 普通用户列表
     */
    public static List<JavaAdmin> getNormalUsers() {
        checkExtension();
        return databaseExtension.findByRole("user");
    }
}