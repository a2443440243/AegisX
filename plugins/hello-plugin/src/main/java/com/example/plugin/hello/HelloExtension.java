package com.example.plugin.hello;

import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 问候插件扩展实现
 * 提供各种问候功能
 */
@Extension
public class HelloExtension implements ExtensionPoint {
    private static final Logger logger = LoggerFactory.getLogger(HelloExtension.class);
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final List<String> greetingHistory = new ArrayList<>();
    
    // 多语言问候语
    private final Map<String, List<String>> greetings = new HashMap<String, List<String>>() {{
        put("chinese", Arrays.asList("你好", "您好", "早上好", "下午好", "晚上好", "欢迎"));
        put("english", Arrays.asList("Hello", "Hi", "Good morning", "Good afternoon", "Good evening", "Welcome"));
        put("japanese", Arrays.asList("こんにちは", "おはよう", "こんばんは", "いらっしゃいませ"));
        put("korean", Arrays.asList("안녕하세요", "좋은 아침", "좋은 저녁", "환영합니다"));
        put("french", Arrays.asList("Bonjour", "Salut", "Bonsoir", "Bienvenue"));
        put("spanish", Arrays.asList("Hola", "Buenos días", "Buenas tardes", "Bienvenido"));
    }};
    
    /**
     * 获取插件名称
     */
    public String getPluginName() {
        return "问候插件";
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
        return "提供多语言问候功能的插件，支持中文、英文、日文、韩文、法文、西班牙文等多种语言";
    }
    
    /**
     * 执行问候操作
     */
    public Object execute(Object input) {
        logger.info("执行问候操作，输入参数: {}", input);
        
        if (input instanceof Map) {
            Map<String, Object> params = (Map<String, Object>) input;
            String action = (String) params.get("action");
            
            switch (action) {
                case "greet":
                    return greetUser(params);
                case "random":
                    return getRandomGreeting(params);
                case "languages":
                    return getSupportedLanguages();
                case "history":
                    return getGreetingHistory();
                case "time":
                    return getTimeBasedGreeting(params);
                default:
                    return Map.of("error", "不支持的操作: " + action);
            }
        }
        
        // 默认返回简单问候
        return getSimpleGreeting();
    }
    
    /**
     * 插件初始化方法
     */
    public void initialize() {
        logger.info("问候插件扩展初始化完成");
        logger.info("支持的语言: {}", String.join(", ", greetings.keySet()));
    }
    
    /**
     * 插件销毁方法
     */
    public void destroy() {
        logger.info("问候插件扩展销毁完成，共产生了 {} 条问候记录", greetingHistory.size());
    }
    
    /**
     * 问候用户
     */
    private Map<String, Object> greetUser(Map<String, Object> params) {
        String name = (String) params.get("name");
        String language = (String) params.getOrDefault("language", "chinese");
        
        if (name == null || name.trim().isEmpty()) {
            return Map.of("success", false, "message", "用户名不能为空");
        }
        
        List<String> languageGreetings = greetings.get(language.toLowerCase());
        if (languageGreetings == null) {
            return Map.of("success", false, "message", "不支持的语言: " + language);
        }
        
        String greeting = languageGreetings.get(0); // 使用第一个问候语
        String message = greeting + ", " + name + "!";
        String timestamp = LocalDateTime.now().format(formatter);
        
        // 记录问候历史
        String record = String.format("[%s] %s (%s)", timestamp, message, language);
        synchronized (greetingHistory) {
            greetingHistory.add(record);
            // 只保留最近100条记录
            if (greetingHistory.size() > 100) {
                greetingHistory.remove(0);
            }
        }
        
        logger.info("问候用户: {}", message);
        
        return Map.of(
            "success", true,
            "message", message,
            "language", language,
            "timestamp", timestamp
        );
    }
    
    /**
     * 获取随机问候语
     */
    private Map<String, Object> getRandomGreeting(Map<String, Object> params) {
        String language = (String) params.getOrDefault("language", "chinese");
        
        List<String> languageGreetings = greetings.get(language.toLowerCase());
        if (languageGreetings == null) {
            return Map.of("success", false, "message", "不支持的语言: " + language);
        }
        
        Random random = new Random();
        String greeting = languageGreetings.get(random.nextInt(languageGreetings.size()));
        String timestamp = LocalDateTime.now().format(formatter);
        
        // 记录问候历史
        String record = String.format("[%s] %s (随机-%s)", timestamp, greeting, language);
        synchronized (greetingHistory) {
            greetingHistory.add(record);
            if (greetingHistory.size() > 100) {
                greetingHistory.remove(0);
            }
        }
        
        return Map.of(
            "success", true,
            "greeting", greeting,
            "language", language,
            "timestamp", timestamp
        );
    }
    
    /**
     * 获取支持的语言列表
     */
    private Map<String, Object> getSupportedLanguages() {
        Map<String, Integer> languageStats = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : greetings.entrySet()) {
            languageStats.put(entry.getKey(), entry.getValue().size());
        }
        
        return Map.of(
            "success", true,
            "languages", new ArrayList<>(greetings.keySet()),
            "languageStats", languageStats,
            "totalLanguages", greetings.size()
        );
    }
    
    /**
     * 获取问候历史
     */
    private Map<String, Object> getGreetingHistory() {
        synchronized (greetingHistory) {
            List<String> history = new ArrayList<>(greetingHistory);
            return Map.of(
                "success", true,
                "history", history,
                "total", history.size()
            );
        }
    }
    
    /**
     * 根据时间获取问候语
     */
    private Map<String, Object> getTimeBasedGreeting(Map<String, Object> params) {
        String language = (String) params.getOrDefault("language", "chinese");
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        
        String greeting;
        String timeOfDay;
        
        if (language.equals("chinese")) {
            if (hour >= 5 && hour < 12) {
                greeting = "早上好";
                timeOfDay = "上午";
            } else if (hour >= 12 && hour < 18) {
                greeting = "下午好";
                timeOfDay = "下午";
            } else {
                greeting = "晚上好";
                timeOfDay = "晚上";
            }
        } else {
            if (hour >= 5 && hour < 12) {
                greeting = "Good morning";
                timeOfDay = "morning";
            } else if (hour >= 12 && hour < 18) {
                greeting = "Good afternoon";
                timeOfDay = "afternoon";
            } else {
                greeting = "Good evening";
                timeOfDay = "evening";
            }
        }
        
        String timestamp = now.format(formatter);
        String record = String.format("[%s] %s (时间问候-%s)", timestamp, greeting, language);
        
        synchronized (greetingHistory) {
            greetingHistory.add(record);
            if (greetingHistory.size() > 100) {
                greetingHistory.remove(0);
            }
        }
        
        return Map.of(
            "success", true,
            "greeting", greeting,
            "timeOfDay", timeOfDay,
            "hour", hour,
            "language", language,
            "timestamp", timestamp
        );
    }
    
    /**
     * 获取简单问候
     */
    private Map<String, Object> getSimpleGreeting() {
        String greeting = "你好，欢迎使用问候插件！";
        String timestamp = LocalDateTime.now().format(formatter);
        
        String record = String.format("[%s] %s (简单问候)", timestamp, greeting);
        synchronized (greetingHistory) {
            greetingHistory.add(record);
            if (greetingHistory.size() > 100) {
                greetingHistory.remove(0);
            }
        }
        
        return Map.of(
            "success", true,
            "message", greeting,
            "timestamp", timestamp
        );
    }
}