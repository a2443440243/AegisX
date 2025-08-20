package com.example.pf4j.controller;

import com.example.pf4j.service.PluginMethodService;
import com.example.pf4jscaffold.common.ApiResponse;
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> executePluginMethod(
            @PathVariable String pluginName,
            @PathVariable String methodName,
            @RequestBody(required = false) Map<String, Object> params) {
        
        logger.info("执行插件方法调用: {}#{}", pluginName, methodName);
        
        try {
            Object result = pluginMethodService.executeMethod(pluginName, methodName, params);
            Map<String, Object> data = Map.of(
                "plugin", pluginName,
                "method", methodName,
                "result", result
            );
            return ResponseEntity.ok(ApiResponse.success(data));
        } catch (Exception e) {
            logger.error("插件方法执行失败: {}#{}, 错误: {}", pluginName, methodName, e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("插件方法执行失败: " + e.getMessage()));
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> executePluginMethodGet(
            @PathVariable String pluginName,
            @PathVariable String methodName) {
        
        logger.info("执行插件方法调用(GET): {}#{}", pluginName, methodName);
        
        try {
            Object result = pluginMethodService.executeMethod(pluginName, methodName, null);
            Map<String, Object> data = Map.of(
                "plugin", pluginName,
                "method", methodName,
                "result", result
            );
            return ResponseEntity.ok(ApiResponse.success(data));
        } catch (Exception e) {
            logger.error("插件方法执行失败(GET): {}#{}, 错误: {}", pluginName, methodName, e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("插件方法执行失败: " + e.getMessage()));
        }
    }
    
    /**
     * 获取插件的所有可调用方法
     * 
     * @param pluginName 插件名称
     * @return 方法列表
     */
    @GetMapping("/{pluginName}/methods")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPluginMethods(@PathVariable String pluginName) {
        try {
            Map<String, Object> methods = pluginMethodService.getPluginMethods(pluginName);
            Map<String, Object> data = Map.of(
                "plugin", pluginName,
                "methods", methods
            );
            return ResponseEntity.ok(ApiResponse.success(data));
        } catch (Exception e) {
            logger.error("获取插件方法列表失败: {}, 错误: {}", pluginName, e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("获取插件方法列表失败: " + e.getMessage()));
        }
    }
}