package com.example.pf4j.config;

import com.example.pf4j.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 配置拦截器和其他Web相关设置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SecurityInterceptor securityInterceptor;

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/api/admin/**") // 只拦截需要管理员权限的API
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/images/**", "/api/**", "/database/api/**", "/actuator/**");
    }
}