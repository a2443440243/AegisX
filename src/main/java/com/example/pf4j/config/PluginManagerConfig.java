package com.example.pf4j.config;

import com.example.pf4j.util.DatabaseUtil;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

/**
 * 插件管理器配置类
 * 负责初始化和配置PF4J插件管理器
 */
@Configuration
public class PluginManagerConfig {

    @Value("${plugin.path:plugins}")
    private String pluginPath;

    @Value("${pf4j.development-mode:false}")
    private boolean developmentMode;

    /**
     * 创建插件管理器Bean
     * @return PluginManager实例
     */
    @Bean
    public PluginManager pluginManager() {
        // 创建默认插件管理器
        DefaultPluginManager pluginManager = new DefaultPluginManager(Paths.get(pluginPath));
        
        // 设置系统版本
        pluginManager.setSystemVersion("1.0.0");
        
        // 开发模式在PF4J中通过其他方式处理
        // 主要影响插件的加载和发现机制
        
        // 启动插件管理器
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        
        // 初始化数据库工具类
        DatabaseUtil.initialize(pluginManager);
        
        return pluginManager;
    }
}