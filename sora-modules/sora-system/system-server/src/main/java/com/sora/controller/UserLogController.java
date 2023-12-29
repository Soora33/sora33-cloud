package com.sora.controller;

import com.sora.domain.XxlJobInfo;
import com.sora.result.Result;
import com.sora.service.UserLogService;
import com.sora.user.UserLog;
import com.sora.utils.XxlUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/jobTest")
    public Result jobTest() {
        List<XxlJobInfo> xxlJobInfos = XxlUtil.pageList(2);
        // 获取id为44的任务
        XxlJobInfo jobInfo = xxlJobInfos.stream()
                .filter(data -> data.getId() == 44)
                .findFirst()
                .orElse(null);
        return Result.success(jobInfo);
    }
}
