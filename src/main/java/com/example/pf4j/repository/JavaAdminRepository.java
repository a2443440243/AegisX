package com.example.pf4j.repository;

import com.example.pf4j.entity.JavaAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * JavaAdmin Repository接口
 * 提供数据库CRUD操作和自定义查询方法
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
@Repository
public interface JavaAdminRepository extends JpaRepository<JavaAdmin, Long>, JpaSpecificationExecutor<JavaAdmin> {
    
    /**
     * 根据用户名查找用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    Optional<JavaAdmin> findByUsername(String username);
    
    /**
     * 根据邮箱查找用户
     * 
     * @param email 邮箱
     * @return 用户信息
     */
    Optional<JavaAdmin> findByEmail(String email);
    
    /**
     * 根据手机号查找用户
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    Optional<JavaAdmin> findByPhone(String phone);
    
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
     * 根据状态和角色查找用户列表
     * 
     * @param status 状态
     * @param role 角色
     * @return 用户列表
     */
    List<JavaAdmin> findByStatusAndRole(Integer status, String role);
    
    /**
     * 根据用户名模糊查询
     * 
     * @param username 用户名关键字
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<JavaAdmin> findByUsernameContaining(String username, Pageable pageable);
    
    /**
     * 根据邮箱模糊查询
     * 
     * @param email 邮箱关键字
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<JavaAdmin> findByEmailContaining(String email, Pageable pageable);
    
    /**
     * 根据创建时间范围查询
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 用户列表
     */
    List<JavaAdmin> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    
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
     * 更新用户状态
     * 
     * @param id 用户ID
     * @param status 新状态
     * @return 影响行数
     */
    @Modifying
    @Transactional
    @Query("UPDATE JavaAdmin j SET j.status = :status, j.updateTime = :updateTime WHERE j.id = :id")
    int updateStatusById(@Param("id") Long id, @Param("status") Integer status, @Param("updateTime") LocalDateTime updateTime);
    
    /**
     * 更新用户密码
     * 
     * @param id 用户ID
     * @param password 新密码
     * @return 影响行数
     */
    @Modifying
    @Transactional
    @Query("UPDATE JavaAdmin j SET j.password = :password, j.updateTime = :updateTime WHERE j.id = :id")
    int updatePasswordById(@Param("id") Long id, @Param("password") String password, @Param("updateTime") LocalDateTime updateTime);
    
    /**
     * 批量更新用户状态
     * 
     * @param ids 用户ID列表
     * @param status 新状态
     * @return 影响行数
     */
    @Modifying
    @Transactional
    @Query("UPDATE JavaAdmin j SET j.status = :status, j.updateTime = :updateTime WHERE j.id IN :ids")
    int updateStatusByIds(@Param("ids") List<Long> ids, @Param("status") Integer status, @Param("updateTime") LocalDateTime updateTime);
    
    /**
     * 删除指定时间之前的用户记录
     * 
     * @param beforeTime 时间点
     * @return 删除数量
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM JavaAdmin j WHERE j.createTime < :beforeTime")
    int deleteByCreateTimeBefore(@Param("beforeTime") LocalDateTime beforeTime);
    
    /**
     * 自定义查询：根据多个条件查询用户
     * 
     * @param username 用户名（可选）
     * @param email 邮箱（可选）
     * @param status 状态（可选）
     * @param role 角色（可选）
     * @param pageable 分页参数
     * @return 分页结果
     */
    @Query("SELECT j FROM JavaAdmin j WHERE " +
           "(:username IS NULL OR j.username LIKE %:username%) AND " +
           "(:email IS NULL OR j.email LIKE %:email%) AND " +
           "(:status IS NULL OR j.status = :status) AND " +
           "(:role IS NULL OR j.role = :role)")
    Page<JavaAdmin> findByMultipleConditions(
            @Param("username") String username,
            @Param("email") String email,
            @Param("status") Integer status,
            @Param("role") String role,
            Pageable pageable);
    
    /**
     * 获取用户统计信息
     * 
     * @return 统计结果：[总数, 启用数, 禁用数, 管理员数, 普通用户数]
     */
    @Query("SELECT " +
           "COUNT(j), " +
           "SUM(CASE WHEN j.status = 1 THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN j.status = 0 THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN j.role = 'admin' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN j.role = 'user' THEN 1 ELSE 0 END) " +
           "FROM JavaAdmin j")
    Object[] getUserStatistics();
}