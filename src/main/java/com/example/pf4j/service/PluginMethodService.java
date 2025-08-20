package com.example.pf4j.service;

import com.example.pf4j.plugin.PluginInterface;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 插件方法调用服务
 * 提供动态调用插件中指定方法的功能
 */
@Service
public class PluginMethodService {
    
    private static final Logger logger = LoggerFactory.getLogger(PluginMethodService.class);
    
    @Autowired
    private PluginManager pluginManager;
    
    @Autowired
    private PluginService pluginService;
    
    /**
     * 执行插件的指定方法
     * 
     * @param pluginName 插件名称
     * @param methodName 方法名称
     * @param params 方法参数
     * @return 方法执行结果
     * @throws Exception 执行异常
     */
    public Object executeMethod(String pluginName, String methodName, Map<String, Object> params) throws Exception {
        logger.info("开始执行插件方法: {}#{}", pluginName, methodName);
        
        // 1. 获取插件实例
        Object pluginInstance = getPluginInstance(pluginName);
        if (pluginInstance == null) {
            throw new IllegalArgumentException("插件未找到: " + pluginName);
        }
        
        // 2. 获取方法
        Method method = findMethod(pluginInstance.getClass(), methodName, params);
        if (method == null) {
            throw new NoSuchMethodException("方法未找到: " + methodName);
        }
        
        // 3. 准备参数
        Object[] args = prepareMethodArguments(method, params);
        
        // 4. 执行方法
        method.setAccessible(true);
        Object result = method.invoke(pluginInstance, args);
        
        logger.info("插件方法执行成功: {}#{}", pluginName, methodName);
        return result;
    }
    
    /**
     * 获取插件实例
     * 
     * @param pluginName 插件名称
     * @return 插件实例
     */
    private Object getPluginInstance(String pluginName) {
        // 首先尝试通过PluginInterface获取
        List<PluginInterface> plugins = pluginManager.getExtensions(PluginInterface.class);
        for (PluginInterface plugin : plugins) {
            if (pluginName.equals(plugin.getPluginName()) || 
                pluginName.equals(plugin.getClass().getSimpleName().toLowerCase().replace("plugin", "").replace("extension", ""))) {
                return plugin;
            }
        }
        
        // 尝试通过所有扩展点获取
        List<Object> allExtensions = pluginManager.getExtensions(Object.class);
        for (Object extension : allExtensions) {
            String className = extension.getClass().getSimpleName().toLowerCase();
            if (className.contains(pluginName.toLowerCase()) || 
                className.replace("extension", "").replace("plugin", "").equals(pluginName.toLowerCase())) {
                return extension;
            }
        }
        
        return null;
    }
    
    /**
     * 查找匹配的方法
     * 
     * @param clazz 类
     * @param methodName 方法名
     * @param params 参数
     * @return 匹配的方法
     */
    private Method findMethod(Class<?> clazz, String methodName, Map<String, Object> params) {
        Method[] methods = clazz.getMethods();
        
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                // 如果没有参数，直接返回无参方法
                if (params == null || params.isEmpty()) {
                    if (method.getParameterCount() == 0) {
                        return method;
                    }
                } else {
                    // 检查参数数量是否匹配
                    if (method.getParameterCount() == params.size()) {
                        return method;
                    }
                }
            }
        }
        
        // 如果没找到精确匹配，尝试找到同名方法（参数数量可能不同）
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        
        return null;
    }
    
    /**
     * 准备方法参数
     * 
     * @param method 方法
     * @param params 参数映射
     * @return 参数数组
     */
    private Object[] prepareMethodArguments(Method method, Map<String, Object> params) {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        
        if (params == null || params.isEmpty()) {
            // 无参数情况，返回空数组或默认值
            for (int i = 0; i < args.length; i++) {
                args[i] = getDefaultValue(parameters[i].getType());
            }
            return args;
        }
        
        // 按参数名称匹配
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            String paramName = param.getName();
            
            if (params.containsKey(paramName)) {
                args[i] = convertParameter(params.get(paramName), param.getType());
            } else {
                // 尝试按索引匹配
                String indexKey = "arg" + i;
                if (params.containsKey(indexKey)) {
                    args[i] = convertParameter(params.get(indexKey), param.getType());
                } else {
                    // 使用默认值
                    args[i] = getDefaultValue(param.getType());
                }
            }
        }
        
        return args;
    }
    
    /**
     * 参数类型转换
     * 
     * @param value 原始值
     * @param targetType 目标类型
     * @return 转换后的值
     */
    private Object convertParameter(Object value, Class<?> targetType) {
        if (value == null) {
            return getDefaultValue(targetType);
        }
        
        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }
        
        // 基本类型转换
        if (targetType == String.class) {
            return value.toString();
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.valueOf(value.toString());
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.valueOf(value.toString());
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.valueOf(value.toString());
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.valueOf(value.toString());
        }
        
        return value;
    }
    
    /**
     * 获取类型的默认值
     * 
     * @param type 类型
     * @return 默认值
     */
    private Object getDefaultValue(Class<?> type) {
        if (type == boolean.class) return false;
        if (type == byte.class) return (byte) 0;
        if (type == short.class) return (short) 0;
        if (type == int.class) return 0;
        if (type == long.class) return 0L;
        if (type == float.class) return 0.0f;
        if (type == double.class) return 0.0d;
        if (type == char.class) return '\0';
        return null;
    }
    
    /**
     * 获取插件的所有可调用方法
     * 
     * @param pluginName 插件名称
     * @return 方法信息映射
     * @throws Exception 异常
     */
    public Map<String, Object> getPluginMethods(String pluginName) throws Exception {
        Object pluginInstance = getPluginInstance(pluginName);
        if (pluginInstance == null) {
            throw new IllegalArgumentException("插件未找到: " + pluginName);
        }
        
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> methods = new ArrayList<>();
        
        Method[] allMethods = pluginInstance.getClass().getMethods();
        for (Method method : allMethods) {
            // 过滤掉Object类的基本方法和一些不需要的方法
            if (shouldIncludeMethod(method)) {
                Map<String, Object> methodInfo = new HashMap<>();
                methodInfo.put("name", method.getName());
                methodInfo.put("returnType", method.getReturnType().getSimpleName());
                
                List<Map<String, String>> params = new ArrayList<>();
                Parameter[] parameters = method.getParameters();
                for (Parameter param : parameters) {
                    Map<String, String> paramInfo = new HashMap<>();
                    paramInfo.put("name", param.getName());
                    paramInfo.put("type", param.getType().getSimpleName());
                    params.add(paramInfo);
                }
                methodInfo.put("parameters", params);
                
                methods.add(methodInfo);
            }
        }
        
        result.put("className", pluginInstance.getClass().getSimpleName());
        result.put("methods", methods);
        return result;
    }
    
    /**
     * 判断是否应该包含该方法
     * 
     * @param method 方法
     * @return 是否包含
     */
    private boolean shouldIncludeMethod(Method method) {
        String methodName = method.getName();
        
        // 排除Object类的基本方法
        if (methodName.equals("equals") || methodName.equals("hashCode") || 
            methodName.equals("toString") || methodName.equals("getClass") ||
            methodName.equals("notify") || methodName.equals("notifyAll") ||
            methodName.equals("wait")) {
            return false;
        }
        
        // 排除getter/setter方法（可选）
        if (methodName.startsWith("get") || methodName.startsWith("set") || methodName.startsWith("is")) {
            return true; // 可以根据需要调整
        }
        
        return true;
    }
}