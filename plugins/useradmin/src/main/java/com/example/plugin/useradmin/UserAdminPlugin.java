package com.example.plugin.useradmin;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户管理插件主类
 * 负责插件的生命周期管理
 */
public class UserAdminPlugin extends Plugin {
    private static final Logger logger = LoggerFactory.getLogger(UserAdminPlugin.class);
    
    public UserAdminPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
    
    @Override
    public void start() {
        logger.info("用户管理插件启动中... 插件ID: {}", getWrapper().getPluginId());
        logger.info("插件版本: {}", getWrapper().getDescriptor().getVersion());
        logger.info("用户管理插件启动完成！");
    }
    
    @Override
    public void stop() {
        logger.info("用户管理插件停止中... 插件ID: {}", getWrapper().getPluginId());
        logger.info("用户管理插件已停止！");
    }
    
    @Override
    public void delete() {
        logger.info("用户管理插件卸载中... 插件ID: {}", getWrapper().getPluginId());
        logger.info("用户管理插件已卸载！");
    }
}