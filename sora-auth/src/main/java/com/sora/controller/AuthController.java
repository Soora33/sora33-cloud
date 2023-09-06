package com.sora.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.sora.result.Result;
import com.sora.service.AuthService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
    
    @Resource
    private ConfigurableEnvironment configurableEnvironment;


    /**
     * login
     * @param userId
     * @param password
     * @return
     */
    @SentinelResource(value = "login")
    @GetMapping("/login/{userId}/{password}")
    public Result selectUserById(@PathVariable("userId") String userId, @PathVariable("password") String password) {;
        PropertySource<?> propertySource = configurableEnvironment.getPropertySources().get("sora-data-properties");
        logger.info("自定义属性值：[{}]", propertySource.getProperty("sora.name"));
        authService.selectUserById(userId, password);
        return Result.success("SUCCESS");
    }


    @SentinelResource(value = "updateUser")
    @PostMapping("/update")
    public Result updateUser(@org.springframework.web.bind.annotation.RequestBody HashMap<String, Object> hashMap) {
        System.out.println(hashMap);
        System.out.println("我是updateUser接口");
        return Result.success("SUCCESS");
    }


    @SentinelResource(value = "exception")
    @PostMapping("/exception")
    public Result exception() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Result.success("SUCCESS");
    }

}
