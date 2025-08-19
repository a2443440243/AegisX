package com.example.pf4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 认证控制器
 * 处理登录相关的页面请求
 */
@Controller
public class AuthController {

    /**
     * 显示登录页面
     * 
     * @param error 是否有错误信息
     * @param logout 是否是登出操作
     * @param model 模型对象
     * @return 登录页面模板名称
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        
        if (error != null) {
            model.addAttribute("errorMsg", "用户名或密码错误！");
        }
        
        if (logout != null) {
            model.addAttribute("logoutMsg", "您已成功登出！");
        }
        
        return "login";
    }
}