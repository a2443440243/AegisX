package com.example.pf4j.controller;

import com.example.pf4j.service.DatabaseConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库配置管理控制器
 * 提供数据库配置的Web界面和API接口
 * 
 * @author PF4J Framework
 * @version 1.0.0
 */
@Controller
@RequestMapping("/database")
public class DatabaseConfigController {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfigController.class);
    
    @Autowired
    private DatabaseConfigService databaseConfigService;
    
    /**
     * 显示数据库配置页面
     * 
     * @param model 页面模型
     * @return 配置页面模板
     */
    @GetMapping("/config")
    public String showConfigPage(Model model) {
        logger.info("访问数据库配置页面");
        
        // 获取当前数据库配置
        Map<String, Object> currentConfig = databaseConfigService.getCurrentConfig();
        model.addAttribute("config", currentConfig);
        
        // 获取连接状态
        boolean isConnected = databaseConfigService.testConnection();
        model.addAttribute("isConnected", isConnected);
        
        return "database/config";
    }
    
    /**
     * 获取当前数据库配置
     * 
     * @return 配置信息
     */
    @GetMapping("/api/config")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCurrentConfig() {
        try {
            Map<String, Object> config = databaseConfigService.getCurrentConfig();
            return ResponseEntity.ok(config);
        } catch (Exception e) {
            logger.error("获取数据库配置失败", e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "获取配置失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * 更新数据库配置
     * 
     * @param configData 配置数据
     * @return 更新结果
     */
    @PostMapping("/api/config")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateConfig(@RequestBody Map<String, String> configData) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            logger.info("更新数据库配置: {}", configData);
            
            // 验证配置参数
            if (!isValidConfig(configData)) {
                result.put("success", false);
                result.put("message", "配置参数不完整");
                return ResponseEntity.badRequest().body(result);
            }
            
            // 更新配置
            boolean success = databaseConfigService.updateConfig(configData);
            
            if (success) {
                result.put("success", true);
                result.put("message", "配置更新成功");
                return ResponseEntity.ok(result);
            } else {
                result.put("success", false);
                result.put("message", "配置更新失败");
                return ResponseEntity.badRequest().body(result);
            }
            
        } catch (Exception e) {
            logger.error("更新数据库配置失败", e);
            result.put("success", false);
            result.put("message", "更新失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 测试数据库连接
     * 
     * @param configData 配置数据（可选，如果为空则测试当前配置）
     * @return 测试结果
     */
    @PostMapping("/api/test")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testConnection(@RequestBody(required = false) Map<String, String> configData) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            boolean isConnected;
            
            if (configData != null && !configData.isEmpty()) {
                // 测试指定配置
                logger.info("测试数据库连接: {}", configData);
                isConnected = databaseConfigService.testConnection(configData);
            } else {
                // 测试当前配置
                logger.info("测试当前数据库连接");
                isConnected = databaseConfigService.testConnection();
            }
            
            result.put("success", isConnected);
            result.put("message", isConnected ? "连接成功" : "连接失败");
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error("测试数据库连接失败", e);
            result.put("success", false);
            result.put("message", "连接测试失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 重新加载数据源
     * 
     * @return 重新加载结果
     */
    @PostMapping("/api/reload")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> reloadDataSource() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            logger.info("重新加载数据源");
            boolean success = databaseConfigService.reloadDataSource();
            
            result.put("success", success);
            result.put("message", success ? "数据源重新加载成功" : "数据源重新加载失败");
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error("重新加载数据源失败", e);
            result.put("success", false);
            result.put("message", "重新加载失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 验证配置参数是否完整
     * 
     * @param config 配置数据
     * @return 是否有效
     */
    private boolean isValidConfig(Map<String, String> config) {
        return config != null 
            && config.containsKey("url") 
            && config.containsKey("username") 
            && config.containsKey("password")
            && config.get("url") != null 
            && !config.get("url").trim().isEmpty()
            && config.get("username") != null 
            && !config.get("username").trim().isEmpty();
    }
}