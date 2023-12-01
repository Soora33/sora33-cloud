package com.sora.controller;

import com.sora.result.Result;
import com.sora.service.UserLogService;
import com.sora.user.UserLog;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname UserLogController
 * @Description 用户日志记录
 * @Date 2023/11/28 13:12
 * @Author by Sora33
 */
@RequestMapping("/userLog")
@RestController
public class UserLogController {

    private final UserLogService userLogService;

    public UserLogController(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

    @Operation(summary = "保存用户操作日志")
    @PostMapping("/insert")
    public Result insert(@RequestBody UserLog userLog) {
        return userLogService.insert(userLog);
    }
}