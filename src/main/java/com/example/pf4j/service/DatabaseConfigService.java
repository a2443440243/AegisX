package com.example.pf4j.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库配置服务
 * 提供动态数据源配置、连接测试等功能
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
@Service
public class DatabaseConfigService {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfigService.class);
    
    @Autowired
    private ConfigurableEnvironment environment;
    
    // 当前数据源配置
    private Map<String, String> currentConfig = new HashMap<>();
    
    // 动态数据源
    private DataSource dynamicDataSource;
    
    /**
     * 获取当前数据库配置
     * 
     * @return 配置信息
     */
    public Map<String, Object> getCurrentConfig() {
        Map<String, Object> config = new HashMap<>();
        
        // 从环境变量获取配置
        String url = environment.getProperty("spring.datasource.url", "");
        String username = environment.getProperty("spring.datasource.username", "");
        String driverClassName = environment.getProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
        
        config.put("url", url);
        config.put("username", username);
        config.put("password", "******"); // 密码不显示
        config.put("driverClassName", driverClassName);
        
        // 解析URL获取数据库信息
        if (url != null && !url.isEmpty()) {
            try {
                String[] parts = url.split("/");
                if (parts.length > 3) {
                    String hostPort = parts[2];
                    String[] hostPortParts = hostPort.split(":");
                    config.put("host", hostPortParts[0]);
                    config.put("port", hostPortParts.length > 1 ? hostPortParts[1] : "3306");
                    
                    String dbPart = parts[3];
                    String dbName = dbPart.contains("?") ? dbPart.split("\\?")[0] : dbPart;
                    config.put("database", dbName);
                }
            } catch (Exception e) {
                logger.warn("解析数据库URL失败: {}", e.getMessage());
            }
        }
        
        return config;
    }
    
    /**
     * 更新数据库配置
     * 
     * @param configData 新的配置数据
     * @return 是否更新成功
     */
    public boolean updateConfig(Map<String, String> configData) {
        try {
            logger.info("开始更新数据库配置");
            
            // 构建新的配置
            Map<String, Object> newProperties = new HashMap<>();
            
            String url = configData.get("url");
            String username = configData.get("username");
            String password = configData.get("password");
            String driverClassName = configData.getOrDefault("driverClassName", "com.mysql.cj.jdbc.Driver");
            
            // 如果是分离的配置，构建URL
            if (url == null || url.isEmpty()) {
                String host = configData.get("host");
                String port = configData.getOrDefault("port", "3306");
                String database = configData.get("database");
                
                if (host != null && database != null) {
                    url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai",
                            host, port, database);
                }
            }
            
            newProperties.put("spring.datasource.url", url);
            newProperties.put("spring.datasource.username", username);
            newProperties.put("spring.datasource.password", password);
            newProperties.put("spring.datasource.driver-class-name", driverClassName);
            
            // 更新环境变量
            MapPropertySource propertySource = new MapPropertySource("dynamicDataSource", newProperties);
            environment.getPropertySources().addFirst(propertySource);
            
            // 保存当前配置
            currentConfig.clear();
            currentConfig.put("url", url);
            currentConfig.put("username", username);
            currentConfig.put("password", password);
            currentConfig.put("driverClassName", driverClassName);
            
            logger.info("数据库配置更新成功");
            return true;
            
        } catch (Exception e) {
            logger.error("更新数据库配置失败", e);
            return false;
        }
    }
    
    /**
     * 测试当前数据库连接
     * 
     * @return 是否连接成功
     */
    public boolean testConnection() {
        try {
            String url = environment.getProperty("spring.datasource.url");
            String username = environment.getProperty("spring.datasource.username");
            String password = environment.getProperty("spring.datasource.password");
            String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
            
            if (url == null || username == null) {
                logger.warn("数据库配置不完整，无法测试连接");
                return false;
            }
            
            Map<String, String> config = new HashMap<>();
            config.put("url", url);
            config.put("username", username);
            config.put("password", password);
            config.put("driverClassName", driverClassName);
            
            return testConnection(config);
            
        } catch (Exception e) {
            logger.error("测试数据库连接失败", e);
            return false;
        }
    }
    
    /**
     * 测试指定配置的数据库连接
     * 
     * @param config 数据库配置
     * @return 是否连接成功
     */
    public boolean testConnection(Map<String, String> config) {
        Connection connection = null;
        try {
            logger.info("开始测试数据库连接");
            
            String url = config.get("url");
            String username = config.get("username");
            String password = config.get("password");
            String driverClassName = config.getOrDefault("driverClassName", "com.mysql.cj.jdbc.Driver");
            
            // 创建临时数据源
            DataSource testDataSource = DataSourceBuilder.create()
                    .url(url)
                    .username(username)
                    .password(password)
                    .driverClassName(driverClassName)
                    .build();
            
            // 测试连接
            connection = testDataSource.getConnection();
            
            if (connection != null && !connection.isClosed()) {
                logger.info("数据库连接测试成功");
                return true;
            } else {
                logger.warn("数据库连接测试失败：连接为空或已关闭");
                return false;
            }
            
        } catch (SQLException e) {
            logger.error("数据库连接测试失败：SQL异常", e);
            return false;
        } catch (Exception e) {
            logger.error("数据库连接测试失败：未知异常", e);
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.warn("关闭测试连接失败", e);
                }
            }
        }
    }
    
    /**
     * 重新加载数据源
     * 
     * @return 是否重新加载成功
     */
    public boolean reloadDataSource() {
        try {
            logger.info("开始重新加载数据源");
            
            // 获取当前配置
            String url = environment.getProperty("spring.datasource.url");
            String username = environment.getProperty("spring.datasource.username");
            String password = environment.getProperty("spring.datasource.password");
            String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
            
            if (url == null || username == null) {
                logger.warn("数据库配置不完整，无法重新加载数据源");
                return false;
            }
            
            // 创建新的数据源
            dynamicDataSource = DataSourceBuilder.create()
                    .url(url)
                    .username(username)
                    .password(password)
                    .driverClassName(driverClassName)
                    .build();
            
            logger.info("数据源重新加载成功");
            return true;
            
        } catch (Exception e) {
            logger.error("重新加载数据源失败", e);
            return false;
        }
    }
    
    /**
     * 获取动态数据源
     * 
     * @return 数据源
     */
    public DataSource getDynamicDataSource() {
        return dynamicDataSource;
    }
}