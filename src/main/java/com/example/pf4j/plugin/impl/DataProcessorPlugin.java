package com.example.pf4j.plugin.impl;

import com.example.pf4j.plugin.PluginInterface;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 数据处理示例插件
 * 演示数据处理和转换功能
 */
@Extension
public class DataProcessorPlugin implements PluginInterface {
    
    private static final Logger logger = LoggerFactory.getLogger(DataProcessorPlugin.class);
    
    @Override
    public String getPluginName() {
        return "DataProcessorPlugin";
    }
    
    @Override
    public String getPluginVersion() {
        return "1.0.0";
    }
    
    @Override
    public String getPluginDescription() {
        return "数据处理插件，提供数据清洗、转换和统计功能";
    }
    
    @Override
    public Object execute(Object input) {
        logger.info("DataProcessorPlugin 开始执行，输入参数: {}", input);
        
        Map<String, Object> result = new HashMap<>();
        result.put("pluginName", getPluginName());
        result.put("timestamp", LocalDateTime.now());
        
        try {
            if (input == null) {
                result.put("error", "输入参数不能为空");
                return result;
            }
            
            // 处理不同类型的输入
            if (input instanceof String) {
                result.putAll(processString((String) input));
            } else if (input instanceof List) {
                result.putAll(processList((List<?>) input));
            } else if (input instanceof Map) {
                result.putAll(processMap((Map<?, ?>) input));
            } else {
                result.put("processedData", input.toString());
                result.put("dataType", input.getClass().getSimpleName());
            }
            
            result.put("success", true);
            
        } catch (Exception e) {
            logger.error("数据处理失败", e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        logger.info("DataProcessorPlugin 执行完成");
        return result;
    }
    
    /**
     * 处理字符串数据
     */
    private Map<String, Object> processString(String input) {
        Map<String, Object> result = new HashMap<>();
        result.put("originalData", input);
        result.put("dataType", "String");
        result.put("length", input.length());
        result.put("upperCase", input.toUpperCase());
        result.put("lowerCase", input.toLowerCase());
        result.put("wordCount", input.split("\\s+").length);
        result.put("reversed", new StringBuilder(input).reverse().toString());
        return result;
    }
    
    /**
     * 处理列表数据
     */
    private Map<String, Object> processList(List<?> input) {
        Map<String, Object> result = new HashMap<>();
        result.put("originalData", input);
        result.put("dataType", "List");
        result.put("size", input.size());
        
        if (!input.isEmpty()) {
            result.put("firstElement", input.get(0));
            result.put("lastElement", input.get(input.size() - 1));
            
            // 如果是数字列表，计算统计信息
            if (input.get(0) instanceof Number) {
                List<Double> numbers = new ArrayList<>();
                for (Object item : input) {
                    if (item instanceof Number) {
                        numbers.add(((Number) item).doubleValue());
                    }
                }
                
                if (!numbers.isEmpty()) {
                    double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();
                    double avg = sum / numbers.size();
                    double max = numbers.stream().mapToDouble(Double::doubleValue).max().orElse(0);
                    double min = numbers.stream().mapToDouble(Double::doubleValue).min().orElse(0);
                    
                    Map<String, Double> statistics = new HashMap<>();
                    statistics.put("sum", sum);
                    statistics.put("average", avg);
                    statistics.put("max", max);
                    statistics.put("min", min);
                    result.put("statistics", statistics);
                }
            }
        }
        
        return result;
    }
    
    /**
     * 处理Map数据
     */
    private Map<String, Object> processMap(Map<?, ?> input) {
        Map<String, Object> result = new HashMap<>();
        result.put("originalData", input);
        result.put("dataType", "Map");
        result.put("keyCount", input.size());
        result.put("keys", new ArrayList<>(input.keySet()));
        
        // 统计值的类型
        Map<String, Integer> valueTypes = new HashMap<>();
        for (Object value : input.values()) {
            String type = value != null ? value.getClass().getSimpleName() : "null";
            valueTypes.put(type, valueTypes.getOrDefault(type, 0) + 1);
        }
        result.put("valueTypes", valueTypes);
        
        return result;
    }
    
    @Override
    public void initialize() {
        logger.info("DataProcessorPlugin 初始化完成");
    }
    
    @Override
    public void destroy() {
        logger.info("DataProcessorPlugin 销毁完成");
    }
}