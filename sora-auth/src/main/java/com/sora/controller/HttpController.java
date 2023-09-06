package com.sora.controller;

import com.sora.result.Result;
import com.sora.utils.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Classname HttpController
 * @Description 网络测试控制器
 * @Date 2023/09/05 13:20
 * @Author by Sora33
 */
@RequestMapping("/http")
@RestController
public class HttpController {

    private static final Logger logger = LoggerFactory.getLogger(HttpController.class);


    @GetMapping("/test/{param}/{method}")
    public Result httpClient(@PathVariable("param") String param, @PathVariable("method") String method) {
        if ("get".equalsIgnoreCase(method)) {
            String url = "http://127.0.0.1:9999/sora/auth/http/" + param;
            HashMap<String, String> headerMap = new HashMap<>();
            headerMap.put("token", "123");
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", param);

            String result = OkHttpUtils.get(url, paramMap, headerMap);
            System.out.println(result);
            return Result.success(result);
        } else if ("post".equalsIgnoreCase(method)) {
            String url = "http://127.0.0.1:9999/sora/auth/http";
            HashMap<String, String> headerMap = new HashMap<>();
            headerMap.put("token", "123");
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", param);
            paramMap.put("name", "11");
            paramMap.put("age", "223");

            String result = OkHttpUtils.post(url, paramMap, headerMap);
            System.out.println(result);
            return Result.success();
        }
        return Result.error();
    }

    @GetMapping("/{id}")
    public Result add(@PathVariable("id") String id) {
        logger.info("Get-请求参数：[{}]", id);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody HashMap<String, Object> paramMap) {
        logger.info("Post-请求参数：[{}]", paramMap);
        return Result.success();
    }
}
