package com.example.pf4jscaffold.controller;

import com.example.pf4jscaffold.common.ApiResponse;
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCurrentConfig() {
        DatabaseConfig config = databaseConfigService.getCurrentConfig().copyWithoutPassword();
        boolean isConnected = databaseConfigService.isConnected();
        boolean configExists = databaseConfigService.getConfigManager().configFileExists();
        
        Map<String, Object> data = new HashMap<>();
        data.put("config", config);
        data.put("isConnected", isConnected);
        data.put("configExists", configExists);
        
        log.debug("API获取配置成功: {}", config.getSummary());
        return ResponseEntity.ok(ApiResponse.success("配置获取成功", data));
    }
    
    /**
     * 更新数据库配置API
     * 
     * @param config 新的配置信息
     * @return 更新结果（JSON格式）
     */
    @PostMapping("/api/config")
    @ResponseBody
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateConfig(@RequestBody DatabaseConfig config) {
        log.info("接收到数据库配置更新请求: {}", config.getSummary());
        
        // 验证配置完整性
        if (!config.isValid()) {
            log.warn("配置验证失败: {}", config.getSummary());
            return ResponseEntity.badRequest().body(ApiResponse.error("配置信息不完整，请检查必填项（URL/主机、用户名、密码）"));
        }
        
        // 尝试更新配置
        boolean success = databaseConfigService.updateConfig(config);
        
        if (success) {
            Map<String, Object> data = new HashMap<>();
            data.put("isConnected", databaseConfigService.isConnected());
            
            log.info("数据库配置更新成功: {}", config.getSummary());
            return ResponseEntity.ok(ApiResponse.success("配置更新成功，数据源已重新加载", data));
        } else {
            log.warn("数据库配置更新失败: {}", config.getSummary());
            return ResponseEntity.badRequest().body(ApiResponse.error("配置更新失败，请检查配置信息和数据库连接"));
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> testConnection(@RequestBody(required = false) DatabaseConfig config) {
        DatabaseConfigService.ConnectionTestResult result;
        
        if (config != null && config.isValid()) {
            log.info("测试指定的数据库配置: {}", config.getSummary());
            result = databaseConfigService.testConnection(config);
        } else {
            log.info("测试当前数据库配置");
            result = databaseConfigService.testCurrentConnection();
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("duration", result.getDuration());
        
        if (result.isSuccess()) {
            log.info("数据库连接测试成功: {}", result.toString());
            return ResponseEntity.ok(ApiResponse.success(result.getDisplayMessage(), data));
        } else {
            log.warn("数据库连接测试失败: {}", result.toString());
            return ResponseEntity.badRequest().body(ApiResponse.error(result.getDisplayMessage()));
        }
    }
    
    /**
     * 重新加载数据源API
     * 
     * @return 操作结果（JSON格式）
     */
    @PostMapping("/api/reload")
    @ResponseBody
    public ResponseEntity<ApiResponse<Map<String, Object>>> reloadDataSource() {
        log.info("手动重新加载数据源");
        
        databaseConfigService.reloadDataSource();
        boolean isConnected = databaseConfigService.isConnected();
        
        Map<String, Object> data = new HashMap<>();
        data.put("isConnected", isConnected);
        
        log.info("数据源重新加载成功，连接状态: {}", isConnected);
        return ResponseEntity.ok(ApiResponse.success("数据源重新加载成功", data));
    }
    
    /**
     * 重置配置为默认值API
     * 
     * @return 操作结果（JSON格式）
     */
    @PostMapping("/api/reset")
    @ResponseBody
    public ResponseEntity<ApiResponse<Map<String, Object>>> resetConfig() {
        log.info("重置数据库配置为默认值");
        
        boolean success = databaseConfigService.resetToDefault();
        
        if (success) {
            Map<String, Object> data = new HashMap<>();
            data.put("config", databaseConfigService.getCurrentConfig().copyWithoutPassword());
            
            log.info("数据库配置重置成功");
            return ResponseEntity.ok(ApiResponse.success("配置已重置为默认值", data));
        } else {
            return ResponseEntity.internalServerError().body(ApiResponse.serverError("重置配置失败"));
        }
    }
    
    /**
     * 获取连接状态API
     * 
     * @return 连接状态信息（JSON格式）
     */
    @GetMapping("/api/status")
    @ResponseBody
    public ResponseEntity<ApiResponse<Map<String, Object>>> getConnectionStatus() {
        boolean isConnected = databaseConfigService.isConnected();
        DatabaseConfig config = databaseConfigService.getCurrentConfig();
        
        Map<String, Object> data = new HashMap<>();
        data.put("isConnected", isConnected);
        data.put("config", config.copyWithoutPassword());
        
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}