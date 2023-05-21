package com.sora.controller;

import com.sora.result.Result;
import com.sora.service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname AuthController
 * @Description 鉴权控制器
 * @Date 2023/05/21 16:05
 * @Author by Sora33
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;


    /**
     * login
     * @param userId
     * @param password
     * @return
     */
    @GetMapping("login/{userId}/{password}")
    public Result selectUserById(@PathVariable("userId") String userId, @PathVariable("password") String password) {
        return authService.selectUserById(userId, password);
    }
}
