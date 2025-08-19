package com.example.pf4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页控制器
 * 处理主页面的请求
 */
@Controller
public class HomeController {

    /**
     * 显示主页面
     * 
     * @return 主页面模板名称
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }
}