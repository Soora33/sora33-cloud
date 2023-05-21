package com.sora.system.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sora.redis.util.RedisUtil;
import com.sora.result.Result;
import com.sora.system.domain.request.User;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/test")
public class SystemController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ObjectMapper objectMapperService;

    @GetMapping("test")
    public Result testController() {
        User user = new User(1, "a", "22", "萨达撒", "asd", new Date());
        try {
            String valueAsString = objectMapperService.writeValueAsString(user);
            User value = objectMapperService.readValue(valueAsString, User.class);
            System.out.println(valueAsString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }
}
