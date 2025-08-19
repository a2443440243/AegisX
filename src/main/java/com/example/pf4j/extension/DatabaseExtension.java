package com.example.pf4j.extension;

import com.example.pf4j.entity.JavaAdmin;
import org.springframework.data.domain.Page;
import org.pf4j.ExtensionPoint;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据库操作扩展点接口
 * 为插件提供统一的数据库访问能力
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
public interface DatabaseExtension extends ExtensionPoint {
    
    // ==================== 基础CRUD操作 ====================
    
    /**
     * 保存用户
     * 
     * @param javaAdmin 用户实体
     * @return 保存后的用户实体
     */
    JavaAdmin save(JavaAdmin javaAdmin);
    
    /**
     * 批量保存用户
     * 
     * @param javaAdmins 用户列表
     * @return 保存后的用户列表
     */
    List<JavaAdmin> saveAll(List<JavaAdmin> javaAdmins);
    
    /**
     * 根据ID查找用户
     * 
     * @param id 用户ID
     * @return 用户实体（可能为空）
     */
    Optional<JavaAdmin> findById(Long id);
    
    /**
     * 根据用户名查找用户
     * 
     * @param username 用户名
     * @return 用户实体（可能为空）
     */
    Optional<JavaAdmin> findByUsername(String username);
    
    /**
     * 查找所有用户
     * 
     * @return 用户列表
     */
    List<JavaAdmin> findAll();
    
    /**
     * 分页查询用户
     * 
     * @param page 页码（从0开始）
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向（ASC/DESC）
     * @return 分页结果
     */
    Page<JavaAdmin> findAll(int page, int size, String sortBy, String sortDir);
    
    /**
     * 删除用户
     * 
     * @param id 用户ID
     */
    void deleteById(Long id);
    
    /**
     * 批量删除用户
     * 
     * @param ids 用户ID列表
     */
    void deleteByIds(List<Long> ids);
    
    // ==================== 业务查询方法 ====================
    
    /**
     * 根据邮箱查找用户
     * 
     * @param email 邮箱
     * @return 用户实体（可能为空）
     */
    Optional<JavaAdmin> findByEmail(String email);
    
    /**
     * 根据手机号查找用户
     * 
     * @param phone 手机号
     * @return 用户实体（可能为空）
     */
    Optional<JavaAdmin> findByPhone(String phone);
    
    /**
     * 根据状态查找用户列表
     * 
     * @param status 状态
     * @return 用户列表
     */
    List<JavaAdmin> findByStatus(Integer status);
    
    /**
     * 根据角色查找用户列表
     * 
     * @param role 角色
     * @return 用户列表
     */
    List<JavaAdmin> findByRole(String role);
    
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
    Page<JavaAdmin> findByConditions(String username, String email, Integer status, String role,
                                   int page, int size, String sortBy, String sortDir);
    
    // ==================== 业务操作方法 ====================
    
    /**
     * 检查用户名是否存在
     * 
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 检查邮箱是否存在
     * 
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);
    
    /**
     * 更新用户状态
     * 
     * @param id 用户ID
     * @param status 新状态
     * @return 是否更新成功
     */
    boolean updateStatus(Long id, Integer status);
    
    /**
     * 更新用户密码
     * 
     * @param id 用户ID
     * @param password 新密码
     * @return 是否更新成功
     */
    boolean updatePassword(Long id, String password);
    
    /**
     * 批量更新用户状态
     * 
     * @param ids 用户ID列表
     * @param status 新状态
     * @return 更新的记录数
     */
    int batchUpdateStatus(List<Long> ids, Integer status);
    
    // ==================== 统计方法 ====================
    
    /**
     * 统计总用户数
     * 
     * @return 用户总数
     */
    long count();
    
    /**
     * 统计指定状态的用户数量
     * 
     * @param status 状态
     * @return 用户数量
     */
    long countByStatus(Integer status);
    
    /**
     * 统计指定角色的用户数量
     * 
     * @param role 角色
     * @return 用户数量
     */
    long countByRole(String role);
    
    /**
     * 获取用户统计信息
     * 
     * @return 统计信息Map
     */
    Map<String, Long> getUserStatistics();
}