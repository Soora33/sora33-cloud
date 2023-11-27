package com.sora.controller;

import com.sora.domain.User;
import com.sora.result.Result;
import com.sora.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "AuthController", description = "鉴权控制器")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private AuthService authService;

    /**
     * login
     *
     * @param name
     * @param password
     * @return
     */
//    @SentinelResource(value = "login")
    @Operation(summary = "登陆", description = "通过用户名与密码登陆")
    @GetMapping("/login/{name}/{password}")
    public Result login(@Parameter(description = "用户名") @PathVariable("name") String name,
                        @Parameter(description = "密码") @PathVariable("password") String password) {
        return authService.login(name, password);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Operation(summary = "注册", description = "注册用户")
    @PostMapping("/register")
    public Result register(@Parameter(description = "用户") @RequestBody User user) {
        return authService.register(user);
    }

}
