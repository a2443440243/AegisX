package com.example.plugin.auto;

import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 自动化任务扩展实现
 * 提供定时任务和批处理功能
 */
@Extension
public class AutoExtension implements ExtensionPoint {
    private static final Logger logger = LoggerFactory.getLogger(AutoExtension.class);
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final List<Map<String, Object>> taskHistory = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public AutoExtension() {
        // 启动一个示例定时任务
        startSampleTask();
    }
    
    /**
     * 获取插件名称
     */
    public String getPluginName() {
        return "自动化任务插件";
    }
    
    /**
     * 获取插件版本
     */
    public String getPluginVersion() {
        return "1.0.0";
    }
    
    /**
     * 获取插件描述
     */
    public String getPluginDescription() {
        return "提供定时任务和批处理功能的自动化插件，支持任务调度和监控";
    }
    
    /**
     * 执行自动化任务操作
     */
    public Object execute(Object input) {
        logger.info("执行自动化任务操作，输入参数: {}", input);
        
        if (input instanceof Map) {
            Map<String, Object> params = (Map<String, Object>) input;
            String action = (String) params.get("action");
            
            switch (action) {
                case "status":
                    return getTaskStatus();
                case "history":
                    return getTaskHistory();
                case "schedule":
                    return scheduleTask(params);
                case "stop":
                    return stopTasks();
                case "restart":
                    return restartTasks();
                default:
                    return Map.of("error", "不支持的操作: " + action);
            }
        }
        
        // 默认返回任务状态
        return getTaskStatus();
    }
    
    /**
     * 插件初始化方法
     */
    public void initialize() {
        logger.info("自动化任务插件扩展初始化完成");
        logger.info("定时任务调度器已启动");
    }
    
    /**
     * 插件销毁方法
     */
    public void destroy() {
        logger.info("自动化任务插件扩展销毁中...");
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        logger.info("自动化任务插件扩展销毁完成");
    }
    
    /**
     * 启动示例定时任务
     */
    private void startSampleTask() {
        // 每30秒执行一次的示例任务
        scheduler.scheduleAtFixedRate(() -> {
            try {
                String timestamp = LocalDateTime.now().format(formatter);
                logger.info("执行定时任务 - {}", timestamp);
                
                // 记录任务执行历史
                Map<String, Object> taskRecord = new HashMap<>();
                taskRecord.put("taskName", "系统监控任务");
                taskRecord.put("executeTime", timestamp);
                taskRecord.put("status", "SUCCESS");
                taskRecord.put("message", "系统运行正常");
                
                synchronized (taskHistory) {
                    taskHistory.add(taskRecord);
                    // 只保留最近50条记录
                    if (taskHistory.size() > 50) {
                        taskHistory.remove(0);
                    }
                }
                
            } catch (Exception e) {
                logger.error("定时任务执行失败", e);
                
                // 记录失败的任务
                Map<String, Object> taskRecord = new HashMap<>();
                taskRecord.put("taskName", "系统监控任务");
                taskRecord.put("executeTime", LocalDateTime.now().format(formatter));
                taskRecord.put("status", "FAILED");
                taskRecord.put("message", "任务执行失败: " + e.getMessage());
                
                synchronized (taskHistory) {
                    taskHistory.add(taskRecord);
                    if (taskHistory.size() > 50) {
                        taskHistory.remove(0);
                    }
                }
            }
        }, 10, 30, TimeUnit.SECONDS); // 延迟10秒启动，然后每30秒执行一次
        
        logger.info("示例定时任务已启动，每30秒执行一次");
    }
    
    /**
     * 获取任务状态
     */
    private Map<String, Object> getTaskStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("schedulerActive", !scheduler.isShutdown());
        status.put("totalTasks", taskHistory.size());
        status.put("lastExecuteTime", taskHistory.isEmpty() ? "无" : 
            taskHistory.get(taskHistory.size() - 1).get("executeTime"));
        status.put("currentTime", LocalDateTime.now().format(formatter));
        
        return Map.of("success", true, "data", status);
    }
    
    /**
     * 获取任务执行历史
     */
    private Map<String, Object> getTaskHistory() {
        synchronized (taskHistory) {
            List<Map<String, Object>> history = new ArrayList<>(taskHistory);
            return Map.of(
                "success", true,
                "data", history,
                "total", history.size()
            );
        }
    }
    
    /**
     * 调度新任务
     */
    private Map<String, Object> scheduleTask(Map<String, Object> params) {
        String taskName = (String) params.get("taskName");
        Integer interval = (Integer) params.get("interval");
        
        if (taskName == null || interval == null || interval <= 0) {
            return Map.of("success", false, "message", "任务名称和执行间隔不能为空");
        }
        
        // 创建新的定时任务
        scheduler.scheduleAtFixedRate(() -> {
            String timestamp = LocalDateTime.now().format(formatter);
            logger.info("执行自定义任务: {} - {}", taskName, timestamp);
            
            Map<String, Object> taskRecord = new HashMap<>();
            taskRecord.put("taskName", taskName);
            taskRecord.put("executeTime", timestamp);
            taskRecord.put("status", "SUCCESS");
            taskRecord.put("message", "自定义任务执行完成");
            
            synchronized (taskHistory) {
                taskHistory.add(taskRecord);
                if (taskHistory.size() > 50) {
                    taskHistory.remove(0);
                }
            }
        }, 5, interval, TimeUnit.SECONDS);
        
        return Map.of(
            "success", true, 
            "message", "任务调度成功",
            "taskName", taskName,
            "interval", interval + "秒"
        );
    }
    
    /**
     * 停止所有任务
     */
    private Map<String, Object> stopTasks() {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
            return Map.of("success", true, "message", "所有任务已停止");
        } else {
            return Map.of("success", false, "message", "任务调度器已经停止");
        }
    }
    
    /**
     * 重启任务调度器
     */
    private Map<String, Object> restartTasks() {
        if (scheduler.isShutdown()) {
            // 注意：实际应用中可能需要重新创建调度器
            return Map.of("success", false, "message", "任务调度器已停止，需要重新初始化插件");
        } else {
            return Map.of("success", true, "message", "任务调度器正在运行中");
        }
    }
}