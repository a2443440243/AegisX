package com.example.pf4j.service;

import com.example.pf4j.plugin.PluginInterface;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 插件服务类
 * 提供插件管理和执行的业务逻辑
 */
@Service
public class PluginService {
    
    private static final Logger logger = LoggerFactory.getLogger(PluginService.class);
    
    @Autowired
    private PluginManager pluginManager;
    
    /**
     * 获取所有已加载的插件
     * @return 插件列表
     */
    public List<PluginInterface> getAllPlugins() {
        return pluginManager.getExtensions(PluginInterface.class);
    }
    
    /**
     * 根据插件名称获取插件
     * @param pluginName 插件名称
     * @return 插件实例
     */
    public PluginInterface getPluginByName(String pluginName) {
        List<PluginInterface> plugins = getAllPlugins();
        return plugins.stream()
                .filter(plugin -> plugin.getPluginName().equals(pluginName))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * 执行指定插件
     * @param pluginName 插件名称
     * @param input 输入参数
     * @return 执行结果
     */
    public Object executePlugin(String pluginName, Object input) {
        try {
            PluginInterface plugin = getPluginByName(pluginName);
            if (plugin != null) {
                logger.info("执行插件: {}", pluginName);
                return plugin.execute(input);
            } else {
                logger.warn("未找到插件: {}", pluginName);
                return "插件未找到: " + pluginName;
            }
        } catch (Exception e) {
            logger.error("执行插件失败: {}", pluginName, e);
            return "插件执行失败: " + e.getMessage();
        }
    }
    
    /**
     * 获取所有插件的信息
     * @return 插件信息映射
     */
    public Map<String, Object> getPluginInfo() {
        List<PluginWrapper> plugins = pluginManager.getPlugins();
        return plugins.stream().collect(Collectors.toMap(
            plugin -> plugin.getPluginId(),
            plugin -> Map.of(
                "id", plugin.getPluginId(),
                "version", plugin.getDescriptor().getVersion(),
                "description", plugin.getDescriptor().getPluginDescription(),
                "state", plugin.getPluginState().toString()
            )
        ));
    }
    
    /**
     * 启动插件
     * @param pluginId 插件ID
     * @return 是否成功
     */
    public boolean startPlugin(String pluginId) {
        try {
            pluginManager.startPlugin(pluginId);
            logger.info("插件启动成功: {}", pluginId);
            return true;
        } catch (Exception e) {
            logger.error("插件启动失败: {}", pluginId, e);
            return false;
        }
    }
    
    /**
     * 停止插件
     * @param pluginId 插件ID
     * @return 是否成功
     */
    public boolean stopPlugin(String pluginId) {
        try {
            pluginManager.stopPlugin(pluginId);
            logger.info("插件停止成功: {}", pluginId);
            return true;
        } catch (Exception e) {
            logger.error("插件停止失败: {}", pluginId, e);
            return false;
        }
    }
    
    /**
     * 重新加载所有插件
     */
    public void reloadPlugins() {
        try {
            pluginManager.stopPlugins();
            pluginManager.unloadPlugins();
            pluginManager.loadPlugins();
            pluginManager.startPlugins();
            logger.info("插件重新加载完成");
        } catch (Exception e) {
            logger.error("插件重新加载失败", e);
        }
    }
    
    /**
     * 获取插件统计信息
     * @return 插件统计数据
     */
    public Map<String, Object> getPluginStats() {
        Map<String, Object> stats = new HashMap<>();
        
        List<PluginWrapper> allPlugins = pluginManager.getPlugins();
        List<PluginInterface> activePlugins = getAllPlugins();
        
        // 总插件数
        stats.put("totalPlugins", allPlugins.size());
        
        // 活跃插件数
        stats.put("activePlugins", activePlugins.size());
        
        // 已启动插件数
        long startedPlugins = allPlugins.stream()
            .filter(plugin -> plugin.getPluginState().toString().equals("STARTED"))
            .count();
        stats.put("startedPlugins", startedPlugins);
        
        // 已停止插件数
        long stoppedPlugins = allPlugins.stream()
            .filter(plugin -> plugin.getPluginState().toString().equals("STOPPED"))
            .count();
        stats.put("stoppedPlugins", stoppedPlugins);
        
        // 失败插件数
        long failedPlugins = allPlugins.stream()
            .filter(plugin -> plugin.getPluginState().toString().equals("FAILED"))
            .count();
        stats.put("failedPlugins", failedPlugins);
        
        // 插件状态分布
        Map<String, Long> statusDistribution = allPlugins.stream()
            .collect(Collectors.groupingBy(
                plugin -> plugin.getPluginState().toString(),
                Collectors.counting()
            ));
        stats.put("statusDistribution", statusDistribution);
        
        return stats;
    }
}