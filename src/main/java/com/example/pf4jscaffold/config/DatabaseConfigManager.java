package com.example.pf4jscaffold.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 数据库配置管理器
 * 负责数据库配置的持久化存储和加载
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
@Slf4j
@Component
public class DatabaseConfigManager {
    
    private static final String CONFIG_DIR = "config";
    private static final String CONFIG_FILE = "database-config.json";
    private final ObjectMapper objectMapper;
    private final Path configFilePath;
    
    public DatabaseConfigManager() {
        this.objectMapper = new ObjectMapper();
        this.configFilePath = Paths.get(CONFIG_DIR, CONFIG_FILE);
        
        // 确保配置目录存在
        try {
            Files.createDirectories(configFilePath.getParent());
        } catch (IOException e) {
            log.error("创建配置目录失败", e);
        }
    }
    
    /**
     * 保存数据库配置到文件
     * 
     * @param config 数据库配置
     * @return 保存是否成功
     */
    public boolean saveConfig(DatabaseConfig config) {
        try {
            // 创建一个不包含密码的配置副本用于存储
            DatabaseConfig configToSave = config.copyWithoutPassword();
            
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(configFilePath.toFile(), configToSave);
            
            log.info("数据库配置已保存到文件: {}", configFilePath);
            return true;
            
        } catch (IOException e) {
            log.error("保存数据库配置失败", e);
            return false;
        }
    }
    
    /**
     * 从文件加载数据库配置
     * 
     * @return 数据库配置，如果文件不存在或加载失败则返回默认配置
     */
    public DatabaseConfig loadConfig() {
        try {
            File configFile = configFilePath.toFile();
            
            if (!configFile.exists()) {
                log.info("配置文件不存在，使用默认配置: {}", configFilePath);
                return DatabaseConfig.createDefault();
            }
            
            DatabaseConfig config = objectMapper.readValue(configFile, DatabaseConfig.class);
            log.info("从文件加载数据库配置成功: {}", configFilePath);
            
            return config;
            
        } catch (IOException e) {
            log.error("加载数据库配置失败，使用默认配置", e);
            return DatabaseConfig.createDefault();
        }
    }
    
    /**
     * 检查配置文件是否存在
     * 
     * @return 配置文件是否存在
     */
    public boolean configFileExists() {
        return Files.exists(configFilePath);
    }
    
    /**
     * 删除配置文件
     * 
     * @return 删除是否成功
     */
    public boolean deleteConfig() {
        try {
            if (Files.exists(configFilePath)) {
                Files.delete(configFilePath);
                log.info("数据库配置文件已删除: {}", configFilePath);
                return true;
            }
            return false;
            
        } catch (IOException e) {
            log.error("删除数据库配置文件失败", e);
            return false;
        }
    }
    
    /**
     * 获取配置文件路径
     * 
     * @return 配置文件路径
     */
    public String getConfigFilePath() {
        return configFilePath.toString();
    }
    
    /**
     * 备份当前配置
     * 
     * @param config 要备份的配置
     * @return 备份是否成功
     */
    public boolean backupConfig(DatabaseConfig config) {
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            Path backupPath = Paths.get(CONFIG_DIR, "database-config-backup-" + timestamp + ".json");
            
            DatabaseConfig configToBackup = config.copyWithoutPassword();
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(backupPath.toFile(), configToBackup);
            
            log.info("数据库配置已备份到: {}", backupPath);
            return true;
            
        } catch (IOException e) {
            log.error("备份数据库配置失败", e);
            return false;
        }
    }
}