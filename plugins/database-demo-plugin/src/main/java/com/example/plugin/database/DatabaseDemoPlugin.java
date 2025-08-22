package com.example.plugin.database;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库演示插件主类
 * 继承自PF4J的Plugin类，实现插件的生命周期管理
 * 
 * @author AegisX Framework
 * @version 1.0.0
 */
public class DatabaseDemoPlugin extends Plugin {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseDemoPlugin.class);
    
    /**
     * 构造函数
     * @param wrapper 插件包装器
     */
    public DatabaseDemoPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    /**
     * 插件启动方法
     * 在插件加载时被调用
     */
    @Override
    public void start() {
        logger.info("=== 数据库演示插件启动中 ===");
        logger.info("插件ID: {}", getWrapper().getPluginId());
        logger.info("插件版本: {}", getWrapper().getDescriptor().getVersion());
        logger.info("插件描述: {}", getWrapper().getDescriptor().getPluginDescription());
        
        try {
            // 执行插件初始化逻辑
            initializePlugin();
            logger.info("数据库演示插件启动成功！");
        } catch (Exception e) {
            logger.error("数据库演示插件启动失败", e);
            throw new RuntimeException("插件启动失败", e);
        }
    }
    
    /**
     * 插件停止方法
     * 在插件卸载时被调用
     */
    @Override
    public void stop() {
        logger.info("=== 数据库演示插件停止中 ===");
        
        try {
            // 执行插件清理逻辑
            cleanupPlugin();
            logger.info("数据库演示插件停止成功！");
        } catch (Exception e) {
            logger.error("数据库演示插件停止时发生错误", e);
        }
    }
    
    /**
     * 删除插件方法
     * 在插件被删除时调用
     */
    @Override
    public void delete() {
        logger.info("=== 数据库演示插件删除中 ===");
        
        try {
            // 执行插件删除前的清理工作
            cleanupPlugin();
            logger.info("数据库演示插件删除成功！");
        } catch (Exception e) {
            logger.error("数据库演示插件删除时发生错误", e);
        }
    }
    
    /**
     * 初始化插件
     * 执行插件启动时的初始化逻辑
     */
    private void initializePlugin() {
        logger.info("正在初始化数据库演示插件...");
        
        // 这里可以添加插件特定的初始化逻辑
        // 例如：注册事件监听器、初始化配置等
        
        logger.info("数据库演示插件初始化完成");
    }
    
    /**
     * 清理插件资源
     * 执行插件停止或删除时的清理逻辑
     */
    private void cleanupPlugin() {
        logger.info("正在清理数据库演示插件资源...");
        
        // 这里可以添加插件特定的清理逻辑
        // 例如：关闭数据库连接、清理缓存、注销事件监听器等
        
        logger.info("数据库演示插件资源清理完成");
    }
}