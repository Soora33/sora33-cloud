package com.sora.controller;

import com.sora.anno.UserLogAnno;
import com.sora.constants.UserLogConstants;
import com.sora.domain.User;
import com.sora.result.Result;
import com.sora.service.UserService;
import com.sora.utils.excel.ExcelUtils;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname UserController
 * @Description 用户控制器
 * @Date 2023/07/01 14:28
 * @Author by Sora33
 */
@RestController
@RequestMapping("/user")
@Tag(name="userController", description = "用户控制器")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "登陆", description = "通过用户名与密码登陆")
    @GetMapping("select/{name}/{password}")
    public Result selectUserList(@Parameter(description = "用户名") @PathVariable("name") String name,
                                 @Parameter(description = "密码") @PathVariable("password") String password) {
        // 从数据库获取所有用户
        return userService.login(name,password);
    }


    @XxlJob("select")
    @UserLogAnno(type = UserLogConstants.SELECT)
    @Operation(summary = "查询所有用户", description = "查找目前用户表内的所有用户")
    @GetMapping("select")
    public Result selectUserList() {
        // 从数据库获取所有用户
        return userService.select();
    }


    @Operation(summary = "插入用户", description = "向用户表插入一条数据")
    @PostMapping("insert")
    public Result selectUserList(@Parameter(description = "用户") @RequestBody User user) {
        // 从数据库获取所有用户
        return userService.insert(user);
    }


    @Operation(summary = "导出用户列表为excel", description = "导出用户表内的所有用户")
    @GetMapping("export/excel")
    public void exportUserList() {
        // 从数据库获取所有用户
        List<User> userList = (List<User>)userService.select().getData();
        // 导出
        ExcelUtils.exportToExcel(userList,"员工信息", User.class);
    }
}
