package com.example.pf4jscaffold.service;

import com.example.pf4jscaffold.config.DatabaseConfig;
import com.example.pf4jscaffold.config.DatabaseConfigManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * 数据库配置服务
 * 负责管理动态数据源配置和连接测试
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
@Slf4j
@Service
public class DatabaseConfigService {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private DatabaseConfigManager configManager;
    
    private DatabaseConfig currentConfig;
    private DataSource currentDataSource;
    
    /**
     * 初始化服务，加载保存的配置
     */
    @PostConstruct
    public void init() {
        try {
            // 从文件加载配置
            currentConfig = configManager.loadConfig();
            log.info("数据库配置服务初始化完成，配置: {}", currentConfig.getSummary());
            
            // 如果配置有效，尝试初始化数据源
            if (currentConfig.isValid()) {
                reloadDataSource();
            }
            
        } catch (Exception e) {
            log.error("数据库配置服务初始化失败", e);
            currentConfig = DatabaseConfig.createDefault();
        }
    }
    
    /**
     * 获取当前数据库配置
     * 
     * @return 当前配置
     */
    public DatabaseConfig getCurrentConfig() {
        if (currentConfig == null) {
            currentConfig = configManager.loadConfig();
        }
        return currentConfig;
    }
    
    /**
     * 更新数据库配置
     * 
     * @param config 新的配置
     * @return 更新是否成功
     */
    public boolean updateConfig(DatabaseConfig config) {
        try {
            log.info("更新数据库配置: {}", config.getSummary());
            
            // 验证配置
            if (!config.isValid()) {
                log.warn("配置信息无效");
                return false;
            }
            
            // 测试新配置的连接
            ConnectionTestResult testResult = testConnection(config);
            if (!testResult.isSuccess()) {
                log.warn("新配置连接测试失败: {}", testResult.getMessage());
                return false;
            }
            
            // 备份当前配置
            if (currentConfig != null && currentConfig.isValid()) {
                configManager.backupConfig(currentConfig);
            }
            
            // 更新配置
            this.currentConfig = config;
            
            // 保存配置到文件
            boolean saved = configManager.saveConfig(config);
            if (!saved) {
                log.warn("配置保存失败，但仍继续更新内存中的配置");
            }
            
            // 重新创建数据源
            reloadDataSource();
            
            log.info("数据库配置更新成功");
            return true;
            
        } catch (Exception e) {
            log.error("更新数据库配置失败", e);
            return false;
        }
    }
    
    /**
     * 测试当前配置的连接
     * 
     * @return 测试结果
     */
    public ConnectionTestResult testCurrentConnection() {
        return testConnection(getCurrentConfig());
    }
    
    /**
     * 测试指定配置的连接
     * 
     * @param config 要测试的配置
     * @return 测试结果
     */
    public ConnectionTestResult testConnection(DatabaseConfig config) {
        if (config == null || !config.isValid()) {
            return new ConnectionTestResult(false, "配置信息无效或不完整", 0);
        }
        
        long startTime = System.currentTimeMillis();
        
        try {
            log.debug("开始测试数据库连接: {}", config.getSummary());
            
            // 创建临时数据源进行测试
            DataSource testDataSource = DataSourceBuilder.create()
                    .url(config.getJdbcUrl())
                    .username(config.getUsername())
                    .password(config.getPassword())
                    .driverClassName(config.getDriverClassName())
                    .build();
            
            // 尝试获取连接并测试
            try (Connection connection = testDataSource.getConnection()) {
                // 执行简单查询测试连接，设置5秒超时
                boolean isValid = connection.isValid(5);
                
                if (isValid) {
                    long duration = System.currentTimeMillis() - startTime;
                    log.info("数据库连接测试成功，耗时: {}ms", duration);
                    return new ConnectionTestResult(true, "连接成功", duration);
                } else {
                    long duration = System.currentTimeMillis() - startTime;
                    return new ConnectionTestResult(false, "连接验证失败", duration);
                }
            }
            
        } catch (SQLException e) {
            long duration = System.currentTimeMillis() - startTime;
            String message = "SQL连接失败: " + e.getMessage();
            log.error("数据库连接测试失败: {}", message, e);
            
            return new ConnectionTestResult(false, message, duration);
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            String message = "连接异常: " + e.getMessage();
            log.error("数据库连接测试异常: {}", message, e);
            
            return new ConnectionTestResult(false, message, duration);
        }
    }
    
    /**
     * 重新加载数据源
     */
    public void reloadDataSource() {
        try {
            DatabaseConfig config = getCurrentConfig();
            
            if (config.isValid()) {
                this.currentDataSource = DataSourceBuilder.create()
                        .url(config.getJdbcUrl())
                        .username(config.getUsername())
                        .password(config.getPassword())
                        .driverClassName(config.getDriverClassName())
                        .build();
                
                log.info("数据源重新加载成功: {}", config.getSummary());
            } else {
                log.warn("配置无效，无法重新加载数据源");
                this.currentDataSource = null;
            }
            
        } catch (Exception e) {
            log.error("重新加载数据源失败", e);
            this.currentDataSource = null;
            throw new RuntimeException("重新加载数据源失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 检查当前是否已连接
     * 
     * @return 是否已连接
     */
    public boolean isConnected() {
        try {
            ConnectionTestResult result = testCurrentConnection();
            return result.isSuccess();
        } catch (Exception e) {
            log.debug("检查连接状态时发生异常", e);
            return false;
        }
    }
    
    /**
     * 获取当前数据源
     * 
     * @return 当前数据源
     */
    public DataSource getCurrentDataSource() {
        if (currentDataSource == null && getCurrentConfig().isValid()) {
            reloadDataSource();
        }
        return currentDataSource;
    }
    
    /**
     * 重置配置为默认值
     * 
     * @return 重置是否成功
     */
    public boolean resetToDefault() {
        try {
            log.info("重置数据库配置为默认值");
            
            // 备份当前配置
            if (currentConfig != null && currentConfig.isValid()) {
                configManager.backupConfig(currentConfig);
            }
            
            // 删除配置文件
            configManager.deleteConfig();
            
            // 重置为默认配置
            this.currentConfig = DatabaseConfig.createDefault();
            this.currentDataSource = null;
            
            log.info("数据库配置已重置为默认值");
            return true;
            
        } catch (Exception e) {
            log.error("重置数据库配置失败", e);
            return false;
        }
    }
    
    /**
     * 获取配置管理器
     * 
     * @return 配置管理器
     */
    public DatabaseConfigManager getConfigManager() {
        return configManager;
    }
    
    /**
     * 连接测试结果
     */
    public static class ConnectionTestResult {
        private final boolean success;
        private final String message;
        private final long duration;
        
        public ConnectionTestResult(boolean success, String message, long duration) {
            this.success = success;
            this.message = message;
            this.duration = duration;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
        
        public long getDuration() {
            return duration;
        }
        
        public String getDisplayMessage() {
            return String.format("%s (耗时: %dms)", message, duration);
        }
        
        @Override
        public String toString() {
            return String.format("ConnectionTestResult{success=%s, message='%s', duration=%dms}", 
                    success, message, duration);
        }
    }
}