package com.example.pf4j.controller;

import com.example.pf4j.plugin.PluginInterface;
import com.example.pf4j.service.PluginService;
import com.example.pf4jscaffold.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * API文档控制器
 * 提供动态API文档生成和在线调用功能
 */
@Controller
@RequestMapping("/api-docs")
@CrossOrigin(origins = "*")
public class ApiDocController {
    
    @Autowired
    private PluginService pluginService;
    
    /**
     * 显示API文档页面
     */
    @GetMapping
    public String apiDocsPage(Model model) {
        // 获取所有插件信息
        List<PluginInterface> plugins = pluginService.getAllPlugins();
        List<Map<String, Object>> pluginDocs = generatePluginDocs(plugins);
        
        model.addAttribute("plugins", pluginDocs);
        model.addAttribute("systemApis", getSystemApis());
        
        return "api-docs";
    }
    

    
    /**
     * 获取插件API文档数据
     */
    @GetMapping("/data")
    @ResponseBody
    public ResponseEntity<ApiResponse<Map<String, Object>>> getApiDocsData() {
        List<PluginInterface> plugins = pluginService.getAllPlugins();
        List<Map<String, Object>> pluginDocs = generatePluginDocs(plugins);
        
        Map<String, Object> data = new HashMap<>();
        data.put("plugins", pluginDocs);
        data.put("systemApis", getSystemApis());
        data.put("count", pluginDocs.size());
        
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    /**
     * 调用插件API
     */
    @PostMapping("/call/{pluginName}")
    @ResponseBody
    public ResponseEntity<ApiResponse<Object>> callPluginApi(
            @PathVariable String pluginName,
            @RequestBody(required = false) Map<String, Object> requestBody) {
        
        try {
            Object input = requestBody != null ? requestBody.get("input") : null;
            Object result = pluginService.executePlugin(pluginName, input);
            
            // 直接返回插件执行结果作为data，保持简洁的响应结构
            return ResponseEntity.ok(ApiResponse.success("插件调用成功", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("插件调用失败: " + e.getMessage()));
        }
    }
    
    /**
     * 生成插件API文档
     */
    private List<Map<String, Object>> generatePluginDocs(List<PluginInterface> plugins) {
        return plugins.stream().map(plugin -> {
            Map<String, Object> doc = new HashMap<>();
            doc.put("name", plugin.getPluginName());
            doc.put("version", plugin.getPluginVersion());
            doc.put("description", plugin.getPluginDescription());
            
            // 分析插件接口方法
            List<Map<String, Object>> methods = analyzePluginMethods(plugin);
            doc.put("methods", methods);
            
            // 生成API端点信息
            Map<String, Object> apiInfo = new HashMap<>();
            apiInfo.put("endpoint", "/api-docs/call/" + plugin.getPluginName());
            apiInfo.put("method", "POST");
            apiInfo.put("contentType", "application/json");
            doc.put("api", apiInfo);
            
            return doc;
        }).collect(Collectors.toList());
    }
    
    /**
     * 分析插件方法
     */
    private List<Map<String, Object>> analyzePluginMethods(PluginInterface plugin) {
        List<Map<String, Object>> methods = new ArrayList<>();
        
        Class<?> pluginClass = plugin.getClass();
        Method[] allMethods = pluginClass.getMethods();
        
        for (Method method : allMethods) {
            // 只分析插件自定义的方法，排除Object和PluginInterface的默认方法
            if (isPluginMethod(method)) {
                Map<String, Object> methodInfo = new HashMap<>();
                methodInfo.put("name", method.getName());
                methodInfo.put("returnType", method.getReturnType().getSimpleName());
                
                // 分析参数
                List<Map<String, String>> parameters = new ArrayList<>();
                Parameter[] params = method.getParameters();
                for (Parameter param : params) {
                    Map<String, String> paramInfo = new HashMap<>();
                    paramInfo.put("name", param.getName());
                    paramInfo.put("type", param.getType().getSimpleName());
                    parameters.add(paramInfo);
                }
                methodInfo.put("parameters", parameters);
                
                methods.add(methodInfo);
            }
        }
        
        return methods;
    }
    
    /**
     * 判断是否为插件自定义方法
     */
    private boolean isPluginMethod(Method method) {
        String methodName = method.getName();
        Class<?> declaringClass = method.getDeclaringClass();
        
        // 排除Object类的方法
        if (declaringClass == Object.class) {
            return false;
        }
        
        // 排除PluginInterface的默认方法
        if (methodName.equals("initialize") || methodName.equals("destroy")) {
            return false;
        }
        
        // 包含主要的插件方法
        return methodName.equals("execute") || 
               methodName.equals("getPluginName") || 
               methodName.equals("getPluginVersion") || 
               methodName.equals("getPluginDescription") ||
               !methodName.startsWith("get") && !methodName.startsWith("set");
    }
    
    /**
     * 获取系统API列表
     */
    private List<Map<String, Object>> getSystemApis() {
        List<Map<String, Object>> systemApis = new ArrayList<>();
        
        // 插件管理API
        systemApis.add(createApiDoc("GET", "/api/plugins", "获取所有插件列表", "获取系统中所有已加载的插件信息"));
        systemApis.add(createApiDoc("GET", "/api/plugins/info", "获取插件详细信息", "获取所有插件的详细状态信息"));
        systemApis.add(createApiDoc("POST", "/api/plugins/execute/{pluginName}", "执行插件", "执行指定名称的插件"));
        systemApis.add(createApiDoc("POST", "/api/plugins/start/{pluginId}", "启动插件", "启动指定ID的插件"));
        systemApis.add(createApiDoc("POST", "/api/plugins/stop/{pluginId}", "停止插件", "停止指定ID的插件"));
        systemApis.add(createApiDoc("POST", "/api/plugins/reload", "重新加载插件", "重新加载所有插件"));
        
        // 系统管理API
        systemApis.add(createApiDoc("GET", "/api/system/status", "系统状态", "获取系统运行状态"));
        systemApis.add(createApiDoc("GET", "/actuator/health", "健康检查", "系统健康状态检查"));
        
        // 数据库API
        systemApis.add(createApiDoc("GET", "/database/api/status", "数据库状态", "获取数据库连接状态"));
        systemApis.add(createApiDoc("POST", "/database/api/config", "数据库配置", "更新数据库配置"));
        
        return systemApis;
    }
    
    /**
     * 创建API文档条目
     */
    private Map<String, Object> createApiDoc(String method, String path, String name, String description) {
        Map<String, Object> api = new HashMap<>();
        api.put("method", method);
        api.put("path", path);
        api.put("name", name);
        api.put("description", description);
        return api;
    }
}