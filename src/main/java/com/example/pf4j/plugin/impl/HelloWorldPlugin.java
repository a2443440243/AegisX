package com.example.pf4j.plugin.impl;

import com.example.pf4j.plugin.PluginInterface;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello World 示例插件
 * 演示插件的基本实现方式
 */
@Extension
public class HelloWorldPlugin implements PluginInterface {
    
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldPlugin.class);
    
    @Override
    public String getPluginName() {
        return "HelloWorldPlugin";
    }
    
    @Override
    public String getPluginVersion() {
        return "1.0.0";
    }
    
    @Override
    public String getPluginDescription() {
        return "一个简单的Hello World示例插件，用于演示插件开发";
    }
    
    @Override
    public Object execute(Object input) {
        logger.info("HelloWorldPlugin 开始执行，输入参数: {}", input);
        
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Hello World from Plugin!");
        result.put("timestamp", LocalDateTime.now());
        result.put("input", input);
        result.put("pluginName", getPluginName());
        result.put("pluginVersion", getPluginVersion());
        
        if (input != null) {
            result.put("processedInput", "处理后的输入: " + input.toString().toUpperCase());
        }
        
        logger.info("HelloWorldPlugin 执行完成，结果: {}", result);
        return result;
    }
    
    @Override
    public void initialize() {
        logger.info("HelloWorldPlugin 初始化完成");
    }
    
    @Override
    public void destroy() {
        logger.info("HelloWorldPlugin 销毁完成");
    }
}