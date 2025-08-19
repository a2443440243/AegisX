package com.example.pf4jscaffold.controller;

import com.example.pf4jscaffold.config.DatabaseConfig;
import com.example.pf4jscaffold.service.DatabaseConfigService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Controller
@RequestMapping("/database")
public class DatabaseConfigController {
    
    @Autowired
    private DatabaseConfigService databaseConfigService;
    
    /**
     * 显示数据库配置页面
     * 
     * @param model 页面模板模型
     * @return 配置页面模板路径
     */
    @GetMapping("/config")
    public String configPage(Model model) {
        try {
            // 获取当前配置（不包含密码）
            DatabaseConfig currentConfig = databaseConfigService.getCurrentConfig().copyWithoutPassword();
            model.addAttribute("config", currentConfig);
            
            // 检查连接状态
            boolean isConnected = databaseConfigService.isConnected();
            model.addAttribute("isConnected", isConnected);
            
            // 添加配置状态信息
            model.addAttribute("configExists", databaseConfigService.getConfigManager().configFileExists());
            
            log.debug("显示数据库配置页面，当前连接状态: {}, 配置摘要: {}", 
                    isConnected, currentConfig.getSummary());
            
        } catch (Exception e) {
            log.error("加载数据库配置页面失败", e);
            model.addAttribute("error", "加载配置失败: " + e.getMessage());
            
            // 提供默认配置以防止页面错误
            model.addAttribute("config", DatabaseConfig.createDefault());
            model.addAttribute("isConnected", false);
            model.addAttribute("configExists", false);
        }
        
        return "database/config";
    }
    
    /**
     * 获取当前数据库配置API
     * 
     * @return 当前配置信息（JSON格式）
     */
    @GetMapping("/api/config")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCurrentConfig() {
        try {
            DatabaseConfig config = databaseConfigService.getCurrentConfig().copyWithoutPassword();
            boolean isConnected = databaseConfigService.isConnected();
            boolean configExists = databaseConfigService.getConfigManager().configFileExists();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("config", config);
            response.put("isConnected", isConnected);
            response.put("configExists", configExists);
            response.put("message", "配置获取成功");
            
            log.debug("API获取配置成功: {}", config.getSummary());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("API获取数据库配置失败", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取配置失败: " + e.getMessage());
            response.put("config", DatabaseConfig.createDefault().copyWithoutPassword());
            response.put("isConnected", false);
            response.put("configExists", false);
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 更新数据库配置API
     * 
     * @param config 新的配置信息
     * @return 更新结果（JSON格式）
     */
    @PostMapping("/api/config")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateConfig(@RequestBody DatabaseConfig config) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("接收到数据库配置更新请求: {}", config.getSummary());
            
            // 验证配置完整性
            if (!config.isValid()) {
                response.put("success", false);
                response.put("message", "配置信息不完整，请检查必填项（URL/主机、用户名、密码）");
                log.warn("配置验证失败: {}", config.getSummary());
                return ResponseEntity.badRequest().body(response);
            }
            
            // 尝试更新配置
            boolean success = databaseConfigService.updateConfig(config);
            
            if (success) {
                response.put("success", true);
                response.put("message", "配置更新成功，数据源已重新加载");
                response.put("isConnected", databaseConfigService.isConnected());
                
                log.info("数据库配置更新成功: {}", config.getSummary());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "配置更新失败，请检查配置信息和数据库连接");
                
                log.warn("数据库配置更新失败: {}", config.getSummary());
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("更新数据库配置时发生异常: {}", config.getSummary(), e);
            
            response.put("success", false);
            response.put("message", "更新失败: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 测试数据库连接API
     * 
     * @param config 要测试的配置（可选，不提供则测试当前配置）
     * @return 测试结果（JSON格式）
     */
    @PostMapping("/api/test")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testConnection(@RequestBody(required = false) DatabaseConfig config) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            DatabaseConfigService.ConnectionTestResult result;
            
            if (config != null && config.isValid()) {
                log.info("测试指定的数据库配置: {}", config.getSummary());
                result = databaseConfigService.testConnection(config);
            } else {
                log.info("测试当前数据库配置");
                result = databaseConfigService.testCurrentConnection();
            }
            
            response.put("success", result.isSuccess());
            response.put("message", result.getDisplayMessage());
            response.put("duration", result.getDuration());
            
            if (result.isSuccess()) {
                log.info("数据库连接测试成功: {}", result.toString());
                return ResponseEntity.ok(response);
            } else {
                log.warn("数据库连接测试失败: {}", result.toString());
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("数据库连接测试时发生异常", e);
            
            response.put("success", false);
            response.put("message", "测试异常: " + e.getMessage());
            response.put("duration", 0);
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 重新加载数据源API
     * 
     * @return 操作结果（JSON格式）
     */
    @PostMapping("/api/reload")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> reloadDataSource() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("手动重新加载数据源");
            
            databaseConfigService.reloadDataSource();
            boolean isConnected = databaseConfigService.isConnected();
            
            response.put("success", true);
            response.put("message", "数据源重新加载成功");
            response.put("isConnected", isConnected);
            
            log.info("数据源重新加载成功，连接状态: {}", isConnected);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("重新加载数据源失败", e);
            
            response.put("success", false);
            response.put("message", "重新加载失败: " + e.getMessage());
            response.put("isConnected", false);
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 重置配置为默认值API
     * 
     * @return 操作结果（JSON格式）
     */
    @PostMapping("/api/reset")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> resetConfig() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("重置数据库配置为默认值");
            
            boolean success = databaseConfigService.resetToDefault();
            
            if (success) {
                response.put("success", true);
                response.put("message", "配置已重置为默认值");
                response.put("config", databaseConfigService.getCurrentConfig().copyWithoutPassword());
                
                log.info("数据库配置重置成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "重置配置失败");
                
                return ResponseEntity.internalServerError().body(response);
            }
            
        } catch (Exception e) {
            log.error("重置数据库配置失败", e);
            
            response.put("success", false);
            response.put("message", "重置失败: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 获取连接状态API
     * 
     * @return 连接状态信息（JSON格式）
     */
    @GetMapping("/api/status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getConnectionStatus() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean isConnected = databaseConfigService.isConnected();
            boolean configExists = databaseConfigService.getConfigManager().configFileExists();
            DatabaseConfig config = databaseConfigService.getCurrentConfig();
            
            response.put("success", true);
            response.put("isConnected", isConnected);
            response.put("configExists", configExists);
            response.put("configSummary", config.getSummary());
            response.put("message", isConnected ? "数据库连接正常" : "数据库未连接");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("获取连接状态失败", e);
            
            response.put("success", false);
            response.put("isConnected", false);
            response.put("configExists", false);
            response.put("message", "获取状态失败: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
}