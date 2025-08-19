package com.example.pf4j.plugin;

import org.pf4j.ExtensionPoint;

/**
 * 插件接口定义
 * 所有插件都需要实现此接口
 */
public interface PluginInterface extends ExtensionPoint {
    
    /**
     * 获取插件名称
     * @return 插件名称
     */
    String getPluginName();
    
    /**
     * 获取插件版本
     * @return 插件版本
     */
    String getPluginVersion();
    
    /**
     * 获取插件描述
     * @return 插件描述
     */
    String getPluginDescription();
    
    /**
     * 执行插件功能
     * @param input 输入参数
     * @return 执行结果
     */
    Object execute(Object input);
    
    /**
     * 插件初始化方法
     */
    default void initialize() {
        // 默认空实现
    }
    
    /**
     * 插件销毁方法
     */
    default void destroy() {
        // 默认空实现
    }
}