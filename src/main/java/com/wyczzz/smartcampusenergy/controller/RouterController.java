package com.wyczzz.smartcampusenergy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {

    // 所有非 API 的错误路径，都转发到 index.html，交给 Vue 路由处理
    // 这样用户在浏览器刷新 /devices 页面时，不会报 404
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/index.html";
    }
}