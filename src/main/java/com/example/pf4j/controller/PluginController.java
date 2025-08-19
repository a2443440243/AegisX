package com.example.pf4j.controller;

import com.example.pf4j.plugin.PluginInterface;
import com.example.pf4j.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 插件管理控制器
 * 提供插件相关的REST API接口
 */
@RestController
@RequestMapping("/api/plugins")
@CrossOrigin(origins = "*")
public class PluginController {
    
    @Autowired
    private PluginService pluginService;
    
    /**
     * 获取所有插件列表
     * @return 插件列表
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPlugins() {
        try {
            List<PluginInterface> plugins = pluginService.getAllPlugins();
            List<Map<String, String>> pluginList = plugins.stream()
                .map(plugin -> {
                    Map<String, String> info = new HashMap<>();
                    info.put("name", plugin.getPluginName());
                    info.put("version", plugin.getPluginVersion());
                    info.put("description", plugin.getPluginDescription());
                    return info;
                })
                .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", pluginList);
            response.put("count", pluginList.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "获取插件列表失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 获取插件详细信息
     * @return 插件详细信息
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getPluginInfo() {
        try {
            Map<String, Object> pluginInfo = pluginService.getPluginInfo();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", pluginInfo);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "获取插件信息失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 执行指定插件
     * @param pluginName 插件名称
     * @param requestBody 请求体，包含输入参数
     * @return 执行结果
     */
    @PostMapping("/execute/{pluginName}")
    public ResponseEntity<Map<String, Object>> executePlugin(
            @PathVariable String pluginName,
            @RequestBody(required = false) Map<String, Object> requestBody) {
        try {
            Object input = requestBody != null ? requestBody.get("input") : null;
            Object result = pluginService.executePlugin(pluginName, input);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("pluginName", pluginName);
            response.put("result", result);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "执行插件失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 启动插件
     * @param pluginId 插件ID
     * @return 操作结果
     */
    @PostMapping("/start/{pluginId}")
    public ResponseEntity<Map<String, Object>> startPlugin(@PathVariable String pluginId) {
        try {
            boolean success = pluginService.startPlugin(pluginId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "插件启动成功" : "插件启动失败");
            response.put("pluginId", pluginId);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "启动插件失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 停止插件
     * @param pluginId 插件ID
     * @return 操作结果
     */
    @PostMapping("/stop/{pluginId}")
    public ResponseEntity<Map<String, Object>> stopPlugin(@PathVariable String pluginId) {
        try {
            boolean success = pluginService.stopPlugin(pluginId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "插件停止成功" : "插件停止失败");
            response.put("pluginId", pluginId);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "停止插件失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 重新加载所有插件
     * @return 操作结果
     */
    @PostMapping("/reload")
    public ResponseEntity<Map<String, Object>> reloadPlugins() {
        try {
            pluginService.reloadPlugins();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "插件重新加载成功");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "重新加载插件失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}