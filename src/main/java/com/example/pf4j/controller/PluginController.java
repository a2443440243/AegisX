package com.example.pf4j.controller;

import com.example.pf4j.plugin.PluginInterface;
import com.example.pf4j.service.PluginService;
import com.example.pf4jscaffold.common.ApiResponse;
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllPlugins() {
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
        
        Map<String, Object> data = new HashMap<>();
        data.put("plugins", pluginList);
        data.put("count", pluginList.size());
        
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    /**
     * 获取插件详细信息
     * @return 插件详细信息
     */
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPluginInfo() {
        Map<String, Object> pluginInfo = pluginService.getPluginInfo();
        return ResponseEntity.ok(ApiResponse.success(pluginInfo));
    }
    
    /**
     * 执行指定插件
     * @param pluginName 插件名称
     * @param requestBody 请求体，包含输入参数
     * @return 执行结果
     */
    @PostMapping("/execute/{pluginName}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> executePlugin(
            @PathVariable String pluginName,
            @RequestBody(required = false) Map<String, Object> requestBody) {
        Object input = requestBody != null ? requestBody.get("input") : null;
        Object result = pluginService.executePlugin(pluginName, input);
        
        Map<String, Object> data = new HashMap<>();
        data.put("pluginName", pluginName);
        data.put("result", result);
        
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    /**
     * 启动插件
     * @param pluginId 插件ID
     * @return 操作结果
     */
    @PostMapping("/start/{pluginId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> startPlugin(@PathVariable String pluginId) {
        boolean success = pluginService.startPlugin(pluginId);
        Map<String, Object> data = new HashMap<>();
        data.put("pluginId", pluginId);
        data.put("started", success);
        
        String message = success ? "插件启动成功" : "插件启动失败";
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }
    
    /**
     * 停止插件
     * @param pluginId 插件ID
     * @return 操作结果
     */
    @PostMapping("/stop/{pluginId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> stopPlugin(@PathVariable String pluginId) {
        boolean success = pluginService.stopPlugin(pluginId);
        Map<String, Object> data = new HashMap<>();
        data.put("pluginId", pluginId);
        data.put("stopped", success);
        
        String message = success ? "插件停止成功" : "插件停止失败";
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }
    
    /**
     * 重新加载所有插件
     * @return 操作结果
     */
    @PostMapping("/reload")
    public ResponseEntity<ApiResponse<Object>> reloadPlugins() {
        pluginService.reloadPlugins();
        return ResponseEntity.ok(ApiResponse.success("插件重新加载成功", null));
    }
}