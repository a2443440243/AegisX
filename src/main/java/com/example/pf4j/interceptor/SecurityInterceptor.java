package com.example.pf4j.interceptor;

import com.example.pf4j.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 安全拦截器
 * 用于拦截API请求并进行权限验证
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        
        logger.debug("拦截请求: {} {}", method, requestURI);
        
        // 对于API请求进行额外的权限检查
        if (requestURI.startsWith("/api/")) {
            if (!authService.isAuthenticated()) {
                logger.warn("未认证用户尝试访问API: {}", requestURI);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"未授权访问\",\"message\":\"请先登录\"}");
                return false;
            }
            
            // 检查管理员权限（所有API操作都需要管理员权限）
            if (!authService.isAdmin()) {
                logger.warn("非管理员用户尝试访问API: {} by {}", requestURI, authService.getCurrentUsername());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"权限不足\",\"message\":\"需要管理员权限\"}");
                return false;
            }
            
            logger.info("管理员 {} 访问API: {}", authService.getCurrentUsername(), requestURI);
        }
        
        return true;
    }
}