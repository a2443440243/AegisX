package com.example.plugin.auto;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动化任务插件主类
 * 负责插件的生命周期管理
 */
public class AutoPlugin extends Plugin {
    private static final Logger logger = LoggerFactory.getLogger(AutoPlugin.class);
    
    public AutoPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    @Override
    public void start() {
        logger.info("自动化任务插件启动中... 插件ID: {}", getWrapper().getPluginId());
        logger.info("插件版本: {}", getWrapper().getDescriptor().getVersion());
        logger.info("自动化任务插件启动完成！");
    }
    
    @Override
    public void stop() {
        logger.info("自动化任务插件停止中... 插件ID: {}", getWrapper().getPluginId());
        logger.info("自动化任务插件已停止！");
    }
    
    @Override
    public void delete() {
        logger.info("自动化任务插件卸载中... 插件ID: {}", getWrapper().getPluginId());
        logger.info("自动化任务插件已卸载！");
    }
}