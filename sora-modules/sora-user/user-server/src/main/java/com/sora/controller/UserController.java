package com.sora.controller;

import com.mybatisflex.core.paginate.Page;
import com.sora.anno.UserLogAnno;
import com.sora.constants.UserLogConstants;
import com.sora.domain.User;
import com.sora.result.Result;
import com.sora.service.UserService;
import com.sora.utils.excel.ExcelUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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


    @Operation(summary = "获取用户菜单权限", description = "通过token获取可查看菜单权限")
    @GetMapping("permissionsByToken/{token}")
    public Result permissionsByToken(@Parameter(description = "token") @PathVariable("token") String token) {
        return userService.permissionsByToken(token);
    }

    @Operation(summary = "获取用户菜单权限", description = "通过用户id获取可查看菜单权限")
    @GetMapping("permissionsById/{id}")
    public Result permissionsById(@Parameter(description = "用户id") @PathVariable("id") String id) {
        return userService.permissionsById(id);
    }

    @Operation(summary = "登陆", description = "通过用户名与密码登陆")
    @GetMapping("select/{name}/{password}")
    public Result login(@Parameter(description = "用户名") @PathVariable("name") String name,
                                 @Parameter(description = "密码") @PathVariable("password") String password) {
        // 从数据库获取所有用户
        return userService.login(name,password);
    }


    @UserLogAnno(type = UserLogConstants.SELECT, description = "查询所有用户")
    @Operation(summary = "查询所有用户", description = "查找目前用户表内的所有用户")
    @GetMapping("selectUserList/{name}/{pageNum}/{pageSize}")
    public Result selectUserList(@PathVariable("name") String name, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        // 从数据库获取所有用户
        return userService.select(name, pageNum, pageSize);
    }


    @Operation(summary = "插入用户", description = "向用户表插入一条数据")
    @PostMapping("insert")
    public Result insertUser(@Parameter(description = "用户") @RequestBody User user) {
        // 从数据库获取所有用户
        return userService.insert(user);
    }


    @Operation(summary = "更新用户", description = "更新用户")
    @PutMapping("update")
    public Result updateUser(@Parameter(description = "用户") @RequestBody User user) {
        // 从数据库获取所有用户
        return userService.update(user);
    }


    @Operation(summary = "导出用户列表为excel", description = "导出用户表内的所有用户")
    @GetMapping("export/excel")
    public void exportUserList() {
        // 从数据库获取所有用户
        Page<User> userList = (Page<User>)userService.select(null, 1, 1000).getData();
        // 导出
        ExcelUtils.exportToExcel(userList.getRecords(),"用户信息", User.class);
    }
}