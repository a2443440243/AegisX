package com.example.pf4j.controller;

import com.example.pf4j.service.PluginMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 插件方法调用控制器
 * 支持动态调用插件中的指定方法
 * 调用格式: /api/plugins/method/{pluginName}/{methodName}
 */
@RestController
@RequestMapping("/api/plugins/method")
public class PluginMethodController {
    
    private static final Logger logger = LoggerFactory.getLogger(PluginMethodController.class);
    
    @Autowired
    private PluginMethodService pluginMethodService;
    
    /**
     * 执行插件的指定方法
     * 
     * @param pluginName 插件名称
     * @param methodName 方法名称
     * @param params 方法参数（可选）
     * @return 方法执行结果
     */
    @PostMapping("/{pluginName}/{methodName}")
    public ResponseEntity<?> executePluginMethod(
            @PathVariable String pluginName,
            @PathVariable String methodName,
            @RequestBody(required = false) Map<String, Object> params) {
        
        logger.info("执行插件方法调用: {}#{}", pluginName, methodName);
        
        try {
            Object result = pluginMethodService.executeMethod(pluginName, methodName, params);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "plugin", pluginName,
                "method", methodName,
                "result", result
            ));
        } catch (Exception e) {
            logger.error("插件方法执行失败: {}#{}", pluginName, methodName, e);
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "plugin", pluginName,
                "method", methodName,
                "error", e.getMessage()
            ));
        }
    }
    
    /**
     * GET方式调用插件方法（无参数）
     * 
     * @param pluginName 插件名称
     * @param methodName 方法名称
     * @return 方法执行结果
     */
    @GetMapping("/{pluginName}/{methodName}")
    public ResponseEntity<?> executePluginMethodGet(
            @PathVariable String pluginName,
            @PathVariable String methodName) {
        
        logger.info("执行插件方法调用(GET): {}#{}", pluginName, methodName);
        
        try {
            Object result = pluginMethodService.executeMethod(pluginName, methodName, null);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "plugin", pluginName,
                "method", methodName,
                "result", result
            ));
        } catch (Exception e) {
            logger.error("插件方法执行失败: {}#{}", pluginName, methodName, e);
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "plugin", pluginName,
                "method", methodName,
                "error", e.getMessage()
            ));
        }
    }
    
    /**
     * 获取插件的所有可调用方法
     * 
     * @param pluginName 插件名称
     * @return 方法列表
     */
    @GetMapping("/{pluginName}/methods")
    public ResponseEntity<?> getPluginMethods(@PathVariable String pluginName) {
        try {
            Map<String, Object> methods = pluginMethodService.getPluginMethods(pluginName);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "plugin", pluginName,
                "methods", methods
            ));
        } catch (Exception e) {
            logger.error("获取插件方法列表失败: {}", pluginName, e);
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "plugin", pluginName,
                "error", e.getMessage()
            ));
        }
    }
}