package com.sora.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.sora.domain.User;
import com.sora.result.Result;
import com.sora.service.AuthService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname AuthController
 * @Description 鉴权控制器
 * @Date 2023/05/21 16:05
 * @Author by Sora33
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private AuthService authService;
    
    /**
     * login
     * @param name
     * @param password
     * @return
     */
    @SentinelResource(value = "login")
    @GetMapping("/login/{name}/{password}")
    public Result login(@PathVariable("name") String name, @PathVariable("password") String password) {
        return authService.login(name,password);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return authService.register(user);
    }

}
