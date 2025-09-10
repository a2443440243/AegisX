package com.example.plugin.hello;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 问候插件主类
 * 负责插件的生命周期管理
 */
public class HelloPlugin extends Plugin {
    private static final Logger logger = LoggerFactory.getLogger(HelloPlugin.class);
    
    public HelloPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    @Override
    public void start() {
        logger.info("问候插件启动中... 插件ID: {}", getWrapper().getPluginId());
        logger.info("插件版本: {}", getWrapper().getDescriptor().getVersion());
        logger.info("Hello World! 问候插件启动完成！");
    }
    
    @Override
    public void stop() {
        logger.info("问候插件停止中... 插件ID: {}", getWrapper().getPluginId());
        logger.info("Goodbye! 问候插件已停止！");
    }
    
    @Override
    public void delete() {
        logger.info("问候插件卸载中... 插件ID: {}", getWrapper().getPluginId());
        logger.info("See you later! 问候插件已卸载！");
    }
}