package com.example.pf4j.service;

import com.example.pf4j.entity.JavaAdmin;
import com.example.pf4j.repository.JavaAdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 统一数据库操作服务类
 * 提供给所有插件使用的数据库操作方法
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DatabaseService {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);
    
    @Autowired
    private JavaAdminRepository javaAdminRepository;
    
    // ==================== 基础CRUD操作 ====================
    
    /**
     * 保存用户
     * 
     * @param javaAdmin 用户实体
     * @return 保存后的用户实体
     */
    public JavaAdmin save(JavaAdmin javaAdmin) {
        try {
            logger.info("保存用户: {}", javaAdmin.getUsername());
            return javaAdminRepository.save(javaAdmin);
        } catch (Exception e) {
            logger.error("保存用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("保存用户失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 批量保存用户
     * 
     * @param javaAdmins 用户列表
     * @return 保存后的用户列表
     */
    public List<JavaAdmin> saveAll(List<JavaAdmin> javaAdmins) {
        try {
            logger.info("批量保存用户，数量: {}", javaAdmins.size());
            return javaAdminRepository.saveAll(javaAdmins);
        } catch (Exception e) {
            logger.error("批量保存用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("批量保存用户失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 根据ID查找用户
     * 
     * @param id 用户ID
     * @return 用户实体（可能为空）
     */
    @Transactional(readOnly = true)
    public Optional<JavaAdmin> findById(Long id) {
        try {
            logger.debug("根据ID查找用户: {}", id);
            return javaAdminRepository.findById(id);
        } catch (Exception e) {
            logger.error("根据ID查找用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("根据ID查找用户失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 根据用户名查找用户
     * 
     * @param username 用户名
     * @return 用户实体（可能为空）
     */
    @Transactional(readOnly = true)
    public Optional<JavaAdmin> findByUsername(String username) {
        try {
            logger.debug("根据用户名查找用户: {}", username);
            return javaAdminRepository.findByUsername(username);
        } catch (Exception e) {
            logger.error("根据用户名查找用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("根据用户名查找用户失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 查找所有用户
     * 
     * @return 用户列表
     */
    @Transactional(readOnly = true)
    public List<JavaAdmin> findAll() {
        try {
            logger.debug("查找所有用户");
            return javaAdminRepository.findAll();
        } catch (Exception e) {
            logger.error("查找所有用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("查找所有用户失败: " + e.getMessage(), e);
        }
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
    @Transactional(readOnly = true)
    public Page<JavaAdmin> findAll(int page, int size, String sortBy, String sortDir) {
        try {
            logger.debug("分页查询用户: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy, sortDir);
            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            return javaAdminRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("分页查询用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("分页查询用户失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 删除用户
     * 
     * @param id 用户ID
     */
    public void deleteById(Long id) {
        try {
            logger.info("删除用户: {}", id);
            javaAdminRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("删除用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("删除用户失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 批量删除用户
     * 
     * @param ids 用户ID列表
     */
    public void deleteByIds(List<Long> ids) {
        try {
            logger.info("批量删除用户，数量: {}", ids.size());
            javaAdminRepository.deleteAllById(ids);
        } catch (Exception e) {
            logger.error("批量删除用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("批量删除用户失败: " + e.getMessage(), e);
        }
    }
    
    // ==================== 业务查询方法 ====================
    
    /**
     * 根据邮箱查找用户
     * 
     * @param email 邮箱
     * @return 用户实体（可能为空）
     */
    @Transactional(readOnly = true)
    public Optional<JavaAdmin> findByEmail(String email) {
        try {
            logger.debug("根据邮箱查找用户: {}", email);
            return javaAdminRepository.findByEmail(email);
        } catch (Exception e) {
            logger.error("根据邮箱查找用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("根据邮箱查找用户失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 根据手机号查找用户
     * 
     * @param phone 手机号
     * @return 用户实体（可能为空）
     */
    @Transactional(readOnly = true)
    public Optional<JavaAdmin> findByPhone(String phone) {
        try {
            logger.debug("根据手机号查找用户: {}", phone);
            return javaAdminRepository.findByPhone(phone);
        } catch (Exception e) {
            logger.error("根据手机号查找用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("根据手机号查找用户失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 根据状态查找用户列表
     * 
     * @param status 状态
     * @return 用户列表
     */
    @Transactional(readOnly = true)
    public List<JavaAdmin> findByStatus(Integer status) {
        try {
            logger.debug("根据状态查找用户: {}", status);
            return javaAdminRepository.findByStatus(status);
        } catch (Exception e) {
            logger.error("根据状态查找用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("根据状态查找用户失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 根据角色查找用户列表
     * 
     * @param role 角色
     * @return 用户列表
     */
    @Transactional(readOnly = true)
    public List<JavaAdmin> findByRole(String role) {
        try {
            logger.debug("根据角色查找用户: {}", role);
            return javaAdminRepository.findByRole(role);
        } catch (Exception e) {
            logger.error("根据角色查找用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("根据角色查找用户失败: " + e.getMessage(), e);
        }
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
    @Transactional(readOnly = true)
    public Page<JavaAdmin> findByConditions(String username, String email, Integer status, String role,
                                           int page, int size, String sortBy, String sortDir) {
        try {
            logger.debug("多条件查询用户: username={}, email={}, status={}, role={}", username, email, status, role);
            
            // 使用Specification进行动态查询
            Specification<JavaAdmin> spec = (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                
                if (StringUtils.hasText(username)) {
                    predicates.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
                }
                if (StringUtils.hasText(email)) {
                    predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
                }
                if (status != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), status));
                }
                if (StringUtils.hasText(role)) {
                    predicates.add(criteriaBuilder.equal(root.get("role"), role));
                }
                
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
            
            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            return javaAdminRepository.findAll(spec, pageable);
        } catch (Exception e) {
            logger.error("多条件查询用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("多条件查询用户失败: " + e.getMessage(), e);
        }
    }
    
    // ==================== 业务操作方法 ====================
    
    /**
     * 检查用户名是否存在
     * 
     * @param username 用户名
     * @return 是否存在
     */
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        try {
            logger.debug("检查用户名是否存在: {}", username);
            return javaAdminRepository.existsByUsername(username);
        } catch (Exception e) {
            logger.error("检查用户名是否存在失败: {}", e.getMessage(), e);
            throw new RuntimeException("检查用户名是否存在失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 检查邮箱是否存在
     * 
     * @param email 邮箱
     * @return 是否存在
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        try {
            logger.debug("检查邮箱是否存在: {}", email);
            return javaAdminRepository.existsByEmail(email);
        } catch (Exception e) {
            logger.error("检查邮箱是否存在失败: {}", e.getMessage(), e);
            throw new RuntimeException("检查邮箱是否存在失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 更新用户状态
     * 
     * @param id 用户ID
     * @param status 新状态
     * @return 是否更新成功
     */
    public boolean updateStatus(Long id, Integer status) {
        try {
            logger.info("更新用户状态: id={}, status={}", id, status);
            int result = javaAdminRepository.updateStatusById(id, status, LocalDateTime.now());
            return result > 0;
        } catch (Exception e) {
            logger.error("更新用户状态失败: {}", e.getMessage(), e);
            throw new RuntimeException("更新用户状态失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 更新用户密码
     * 
     * @param id 用户ID
     * @param password 新密码
     * @return 是否更新成功
     */
    public boolean updatePassword(Long id, String password) {
        try {
            logger.info("更新用户密码: id={}", id);
            int result = javaAdminRepository.updatePasswordById(id, password, LocalDateTime.now());
            return result > 0;
        } catch (Exception e) {
            logger.error("更新用户密码失败: {}", e.getMessage(), e);
            throw new RuntimeException("更新用户密码失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 批量更新用户状态
     * 
     * @param ids 用户ID列表
     * @param status 新状态
     * @return 更新的记录数
     */
    public int batchUpdateStatus(List<Long> ids, Integer status) {
        try {
            logger.info("批量更新用户状态: ids={}, status={}", ids, status);
            return javaAdminRepository.updateStatusByIds(ids, status, LocalDateTime.now());
        } catch (Exception e) {
            logger.error("批量更新用户状态失败: {}", e.getMessage(), e);
            throw new RuntimeException("批量更新用户状态失败: " + e.getMessage(), e);
        }
    }
    
    // ==================== 统计方法 ====================
    
    /**
     * 统计总用户数
     * 
     * @return 用户总数
     */
    @Transactional(readOnly = true)
    public long count() {
        try {
            logger.debug("统计总用户数");
            return javaAdminRepository.count();
        } catch (Exception e) {
            logger.error("统计总用户数失败: {}", e.getMessage(), e);
            throw new RuntimeException("统计总用户数失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 统计指定状态的用户数量
     * 
     * @param status 状态
     * @return 用户数量
     */
    @Transactional(readOnly = true)
    public long countByStatus(Integer status) {
        try {
            logger.debug("统计指定状态的用户数量: {}", status);
            return javaAdminRepository.countByStatus(status);
        } catch (Exception e) {
            logger.error("统计指定状态的用户数量失败: {}", e.getMessage(), e);
            throw new RuntimeException("统计指定状态的用户数量失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 统计指定角色的用户数量
     * 
     * @param role 角色
     * @return 用户数量
     */
    @Transactional(readOnly = true)
    public long countByRole(String role) {
        try {
            logger.debug("统计指定角色的用户数量: {}", role);
            return javaAdminRepository.countByRole(role);
        } catch (Exception e) {
            logger.error("统计指定角色的用户数量失败: {}", e.getMessage(), e);
            throw new RuntimeException("统计指定角色的用户数量失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 获取用户统计信息
     * 
     * @return 统计信息Map
     */
    @Transactional(readOnly = true)
    public java.util.Map<String, Long> getUserStatistics() {
        try {
            logger.debug("获取用户统计信息");
            Object[] result = javaAdminRepository.getUserStatistics();
            
            java.util.Map<String, Long> statistics = new java.util.HashMap<>();
            statistics.put("total", ((Number) result[0]).longValue());
            statistics.put("enabled", ((Number) result[1]).longValue());
            statistics.put("disabled", ((Number) result[2]).longValue());
            statistics.put("admin", ((Number) result[3]).longValue());
            statistics.put("user", ((Number) result[4]).longValue());
            
            return statistics;
        } catch (Exception e) {
            logger.error("获取用户统计信息失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取用户统计信息失败: " + e.getMessage(), e);
        }
    }
}