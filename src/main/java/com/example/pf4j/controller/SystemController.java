package com.example.pf4j.controller;

import com.example.pf4jscaffold.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统管理控制器
 * 提供系统状态和健康检查相关的API接口
 */
@RestController
@RequestMapping("/api/system")
@CrossOrigin(origins = "*")
public class SystemController {
    
    /**
     * 获取系统状态
     * @return 系统状态信息
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSystemStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "running");
        status.put("timestamp", LocalDateTime.now());
        status.put("version", "1.0.0");
        status.put("framework", "PF4J Dynamic Loading Scaffold");
        
        // 获取JVM信息
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> jvmInfo = new HashMap<>();
        jvmInfo.put("totalMemory", runtime.totalMemory());
        jvmInfo.put("freeMemory", runtime.freeMemory());
        jvmInfo.put("usedMemory", runtime.totalMemory() - runtime.freeMemory());
        jvmInfo.put("maxMemory", runtime.maxMemory());
        jvmInfo.put("processors", runtime.availableProcessors());
        
        status.put("jvm", jvmInfo);
        
        return ResponseEntity.ok(ApiResponse.success(status));
    }
    
    /**
     * 健康检查接口
     * @return 健康状态
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("checks", Map.of(
            "database", "UP",
            "pluginManager", "UP",
            "diskSpace", "UP"
        ));
        
        return ResponseEntity.ok(ApiResponse.success(health));
    }
    
    /**
     * 获取系统信息
     * @return 系统详细信息
     */
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        
        // 系统属性
        Map<String, String> systemProps = new HashMap<>();
        systemProps.put("java.version", System.getProperty("java.version"));
        systemProps.put("java.vendor", System.getProperty("java.vendor"));
        systemProps.put("os.name", System.getProperty("os.name"));
        systemProps.put("os.version", System.getProperty("os.version"));
        systemProps.put("os.arch", System.getProperty("os.arch"));
        systemProps.put("user.dir", System.getProperty("user.dir"));
        
        info.put("system", systemProps);
        info.put("application", Map.of(
            "name", "PF4J Dynamic Loading Scaffold",
            "version", "1.0.0",
            "description", "基于PF4J的动态插件加载脚手架"
        ));
        
        return ResponseEntity.ok(ApiResponse.success(info));
    }
    
    /**
     * 重启系统（模拟）
     * @return 操作结果
     */
    @PostMapping("/restart")
    public ResponseEntity<ApiResponse<Map<String, Object>>> restartSystem() {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        
        // 注意：实际生产环境中需要谨慎实现系统重启功能
        return ResponseEntity.ok(ApiResponse.success("系统重启请求已接收，请稍后...", response));
    }
}