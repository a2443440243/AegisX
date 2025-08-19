package com.example.pf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * PF4J动态加载脚手架主启动类
 * 
 * 这是一个基于Spring Boot和PF4J框架的动态插件加载系统
 * 支持插件的热加载、热卸载和动态管理
 */
@SpringBootApplication
public class Pf4jScaffoldApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(Pf4jScaffoldApplication.class);
    
    public static void main(String[] args) {
        try {
            logger.info("========================================");
            logger.info("PF4J Dynamic Loading Scaffold 启动中...");
            logger.info("========================================");
            
            ConfigurableApplicationContext context = SpringApplication.run(Pf4jScaffoldApplication.class, args);
            
            // 获取应用信息
            String appName = context.getEnvironment().getProperty("spring.application.name", "pf4j-scaffold");
            String port = context.getEnvironment().getProperty("server.port", "8080");
            String contextPath = context.getEnvironment().getProperty("server.servlet.context-path", "/");
            
            logger.info("========================================");
            logger.info("应用启动成功！");
            logger.info("应用名称: {}", appName);
            logger.info("访问地址: http://localhost:{}{}", port, contextPath);
            logger.info("API文档: http://localhost:{}/api", port);
            logger.info("健康检查: http://localhost:{}/actuator/health", port);
            logger.info("插件管理: http://localhost:{}/api/plugins", port);
            logger.info("系统状态: http://localhost:{}/api/system/status", port);
            logger.info("========================================");
            
            // 注册关闭钩子
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("应用正在关闭...");
                context.close();
                logger.info("应用已关闭");
            }));
            
        } catch (Exception e) {
            logger.error("应用启动失败", e);
            System.exit(1);
        }
    }
}