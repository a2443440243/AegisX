package com.example.pf4j.extension.impl;

import com.example.pf4j.entity.JavaAdmin;
import com.example.pf4j.extension.DatabaseExtension;
import com.example.pf4j.service.DatabaseService;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 默认数据库扩展实现
 * 提供统一的数据库操作接口给插件使用
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
@Extension
@Component
public class DefaultDatabaseExtension implements DatabaseExtension, ApplicationContextAware {
    
    private static final Logger logger = LoggerFactory.getLogger(DefaultDatabaseExtension.class);
    
    private DatabaseService databaseService;
    private ApplicationContext applicationContext;
    
    /**
     * 无参构造函数 - PF4J扩展实例化需要
     */
    public DefaultDatabaseExtension() {
        logger.info("默认数据库扩展创建");
    }
    
    /**
     * 构造函数 - Spring依赖注入使用
     * 
     * @param databaseService 数据库服务
     */
    @Autowired
    public DefaultDatabaseExtension(DatabaseService databaseService) {
        this.databaseService = databaseService;
        logger.info("默认数据库扩展初始化完成");
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        if (this.databaseService == null && applicationContext != null) {
            this.databaseService = applicationContext.getBean(DatabaseService.class);
            logger.info("通过ApplicationContext获取DatabaseService成功");
        }
    }
    
    /**
     * 获取数据库服务实例
     */
    private DatabaseService getDatabaseService() {
        if (databaseService == null && applicationContext != null) {
            databaseService = applicationContext.getBean(DatabaseService.class);
        }
        if (databaseService == null) {
            throw new RuntimeException("DatabaseService未初始化，请检查Spring配置");
        }
        return databaseService;
    }
    
    // ==================== 基础CRUD操作 ====================
    
    @Override
    public JavaAdmin save(JavaAdmin javaAdmin) {
        logger.debug("插件调用数据库保存操作: {}", javaAdmin.getUsername());
        return getDatabaseService().save(javaAdmin);
    }
    
    @Override
    public List<JavaAdmin> saveAll(List<JavaAdmin> javaAdmins) {
        logger.debug("插件调用数据库批量保存操作，数量: {}", javaAdmins.size());
        return getDatabaseService().saveAll(javaAdmins);
    }
    
    @Override
    public Optional<JavaAdmin> findById(Long id) {
        logger.debug("插件调用根据ID查找用户: {}", id);
        return getDatabaseService().findById(id);
    }
    
    @Override
    public Optional<JavaAdmin> findByUsername(String username) {
        logger.debug("插件调用根据用户名查找用户: {}", username);
        return getDatabaseService().findByUsername(username);
    }
    
    @Override
    public List<JavaAdmin> findAll() {
        logger.debug("插件调用查找所有用户");
        return getDatabaseService().findAll();
    }
    
    @Override
    public Page<JavaAdmin> findAll(int page, int size, String sortBy, String sortDir) {
        logger.debug("插件调用分页查询用户: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy, sortDir);
        return getDatabaseService().findAll(page, size, sortBy, sortDir);
    }
    
    @Override
    public void deleteById(Long id) {
        logger.debug("插件调用删除用户: {}", id);
        getDatabaseService().deleteById(id);
    }
    
    @Override
    public void deleteByIds(List<Long> ids) {
        logger.debug("插件调用批量删除用户，数量: {}", ids.size());
        getDatabaseService().deleteByIds(ids);
    }
    
    // ==================== 业务查询方法 ====================
    
    @Override
    public Optional<JavaAdmin> findByEmail(String email) {
        logger.debug("插件调用根据邮箱查找用户: {}", email);
        return getDatabaseService().findByEmail(email);
    }
    
    @Override
    public Optional<JavaAdmin> findByPhone(String phone) {
        logger.debug("插件调用根据手机号查找用户: {}", phone);
        return getDatabaseService().findByPhone(phone);
    }
    
    @Override
    public List<JavaAdmin> findByStatus(Integer status) {
        logger.debug("插件调用根据状态查找用户: {}", status);
        return getDatabaseService().findByStatus(status);
    }
    
    @Override
    public List<JavaAdmin> findByRole(String role) {
        logger.debug("插件调用根据角色查找用户: {}", role);
        return getDatabaseService().findByRole(role);
    }
    
    @Override
    public Page<JavaAdmin> findByConditions(String username, String email, Integer status, String role,
                                          int page, int size, String sortBy, String sortDir) {
        logger.debug("插件调用多条件查询用户: username={}, email={}, status={}, role={}", username, email, status, role);
        return getDatabaseService().findByConditions(username, email, status, role, page, size, sortBy, sortDir);
    }
    
    // ==================== 业务操作方法 ====================
    
    @Override
    public boolean existsByUsername(String username) {
        logger.debug("插件调用检查用户名是否存在: {}", username);
        return getDatabaseService().existsByUsername(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        logger.debug("插件调用检查邮箱是否存在: {}", email);
        return getDatabaseService().existsByEmail(email);
    }
    
    @Override
    public boolean updateStatus(Long id, Integer status) {
        logger.debug("插件调用更新用户状态: id={}, status={}", id, status);
        return getDatabaseService().updateStatus(id, status);
    }
    
    @Override
    public boolean updatePassword(Long id, String password) {
        logger.debug("插件调用更新用户密码: id={}", id);
        return getDatabaseService().updatePassword(id, password);
    }
    
    @Override
    public int batchUpdateStatus(List<Long> ids, Integer status) {
        logger.debug("插件调用批量更新用户状态: ids={}, status={}", ids, status);
        return getDatabaseService().batchUpdateStatus(ids, status);
    }
    
    // ==================== 统计方法 ====================
    
    @Override
    public long count() {
        logger.debug("插件调用统计总用户数");
        return getDatabaseService().count();
    }
    
    @Override
    public long countByStatus(Integer status) {
        logger.debug("插件调用统计指定状态的用户数量: {}", status);
        return getDatabaseService().countByStatus(status);
    }
    
    @Override
    public long countByRole(String role) {
        logger.debug("插件调用统计指定角色的用户数量: {}", role);
        return getDatabaseService().countByRole(role);
    }
    
    @Override
    public Map<String, Long> getUserStatistics() {
        logger.debug("插件调用获取用户统计信息");
        return getDatabaseService().getUserStatistics();
    }
}