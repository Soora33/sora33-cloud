package com.sora.controller;

import com.sora.result.Result;
import com.sora.service.UserLogService;
import com.sora.user.UserLog;
import com.sora.utils.XxlUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @GetMapping("/addJob")
    public Result addJob() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("JobDesc","修改任务");
        paramMap.put("ExecutorHandler","testHandler");
        paramMap.put("Author","sora33");
        paramMap.put("ScheduleType","CRON");
        paramMap.put("ScheduleConf","0/10 * * * * ?");
        paramMap.put("GlueType","BEAN");
        paramMap.put("ExecutorRouteStrategy","FIRST");
        paramMap.put("MisfireStrategy","DO_NOTHING");
        paramMap.put("ExecutorBlockStrategy","SERIAL_EXECUTION");
        paramMap.put("TriggerStatus",0);
        paramMap.put("JobGroup",2);
        String string = XxlUtil.addJob(paramMap);
        System.out.println(string);
        return Result.success(null);
    }
}
