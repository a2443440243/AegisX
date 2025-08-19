package com.example.pf4jscaffold.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import java.io.Serializable;

/**
 * 数据库配置实体类
 * 用于存储和管理数据库连接配置信息
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatabaseConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 数据库连接URL
     * 完整的JDBC连接字符串
     */
    private String url;
    
    /**
     * 数据库主机地址
     */
    private String host;
    
    /**
     * 数据库端口
     */
    @Min(value = 1, message = "端口号必须大于0")
    @Max(value = 65535, message = "端口号不能超过65535")
    private Integer port;
    
    /**
     * 数据库名称
     */
    private String database;
    
    /**
     * 数据库用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    /**
     * 数据库密码
     */
    private String password;
    
    /**
     * 数据库驱动类名
     */
    @Builder.Default
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    
    /**
     * 连接池最大连接数
     */
    @Builder.Default
    private Integer maxPoolSize = 10;
    
    /**
     * 连接池最小连接数
     */
    @Builder.Default
    private Integer minPoolSize = 1;
    
    /**
     * 连接超时时间（毫秒）
     */
    @Builder.Default
    private Long connectionTimeout = 30000L;
    
    /**
     * 空闲连接超时时间（毫秒）
     */
    @Builder.Default
    private Long idleTimeout = 600000L;
    
    /**
     * 连接最大生命周期（毫秒）
     */
    @Builder.Default
    private Long maxLifetime = 1800000L;
    
    /**
     * 获取完整的JDBC URL
     * 如果url字段不为空则直接返回，否则根据host、port、database构建
     * 
     * @return JDBC连接URL
     */
    public String getJdbcUrl() {
        if (url != null && !url.trim().isEmpty()) {
            return url.trim();
        }
        
        if (host != null && port != null && database != null) {
            return String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true",
                    host.trim(), port, database.trim());
        }
        
        return null;
    }
    
    /**
     * 验证配置是否完整
     * 
     * @return 配置是否有效
     */
    public boolean isValid() {
        // 检查用户名是否为空
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        
        // 检查是否有有效的连接信息
        boolean hasUrl = url != null && !url.trim().isEmpty();
        boolean hasHostPortDb = host != null && !host.trim().isEmpty() && 
                               port != null && port > 0 && port <= 65535 &&
                               database != null && !database.trim().isEmpty();
        
        return hasUrl || hasHostPortDb;
    }
    
    /**
     * 获取配置摘要信息（不包含密码）
     * 
     * @return 配置摘要
     */
    public String getSummary() {
        String jdbcUrl = getJdbcUrl();
        if (jdbcUrl != null) {
            // 移除密码信息
            String safeSummary = jdbcUrl.replaceAll("password=[^&]*", "password=***");
            return String.format("用户: %s, URL: %s", username, safeSummary);
        }
        return String.format("用户: %s, 主机: %s:%s, 数据库: %s", 
                username, host, port, database);
    }
    
    /**
     * 创建默认配置
     * 
     * @return 默认数据库配置
     */
    public static DatabaseConfig createDefault() {
        return DatabaseConfig.builder()
                .host("localhost")
                .port(3306)
                .database("pf4j_db")
                .username("root")
                .password("")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .maxPoolSize(10)
                .minPoolSize(1)
                .connectionTimeout(30000L)
                .idleTimeout(600000L)
                .maxLifetime(1800000L)
                .build();
    }
    
    /**
     * 从URL解析配置信息
     * 
     * @param jdbcUrl JDBC连接URL
     * @return 解析后的配置对象
     */
    public static DatabaseConfig fromUrl(String jdbcUrl) {
        DatabaseConfig config = createDefault();
        config.setUrl(jdbcUrl);
        
        try {
            // 简单的URL解析
            if (jdbcUrl != null && jdbcUrl.startsWith("jdbc:mysql://")) {
                String urlPart = jdbcUrl.substring("jdbc:mysql://".length());
                String[] parts = urlPart.split("/");
                
                if (parts.length >= 2) {
                    String hostPort = parts[0];
                    String dbPart = parts[1];
                    
                    // 解析主机和端口
                    if (hostPort.contains(":")) {
                        String[] hostPortParts = hostPort.split(":");
                        config.setHost(hostPortParts[0]);
                        try {
                            config.setPort(Integer.parseInt(hostPortParts[1]));
                        } catch (NumberFormatException e) {
                            // 忽略端口解析错误
                        }
                    } else {
                        config.setHost(hostPort);
                    }
                    
                    // 解析数据库名
                    if (dbPart.contains("?")) {
                        config.setDatabase(dbPart.split("\\?")[0]);
                    } else {
                        config.setDatabase(dbPart);
                    }
                }
            }
        } catch (Exception e) {
            // 解析失败时保持URL不变
        }
        
        return config;
    }
    
    /**
     * 复制配置（不包含密码）
     * 
     * @return 不包含密码的配置副本
     */
    public DatabaseConfig copyWithoutPassword() {
        return DatabaseConfig.builder()
                .url(this.url)
                .host(this.host)
                .port(this.port)
                .database(this.database)
                .username(this.username)
                .password(null) // 不复制密码
                .driverClassName(this.driverClassName)
                .maxPoolSize(this.maxPoolSize)
                .minPoolSize(this.minPoolSize)
                .connectionTimeout(this.connectionTimeout)
                .idleTimeout(this.idleTimeout)
                .maxLifetime(this.maxLifetime)
                .build();
    }
}