package com.example.plugin.database;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作示例插件
 * 演示如何使用框架提供的数据库操作功能
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
public class DatabaseDemoPlugin extends Plugin {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseDemoPlugin.class);
    
    /**
     * 构造函数
     * 
     * @param wrapper 插件包装器
     */
    public DatabaseDemoPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    @Override
    public void start() {
        logger.info("数据库操作示例插件启动中...");
        logger.info("插件ID: {}", wrapper.getPluginId());
        logger.info("插件版本: {}", wrapper.getDescriptor().getVersion());
        logger.info("数据库操作示例插件启动完成");
    }
    
    @Override
    public void stop() {
        logger.info("数据库操作示例插件停止");
    }
    
    @Override
    public void delete() {
        logger.info("数据库操作示例插件删除");
    }

}