package com.sora.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sora.domain.User;
import com.sora.result.Result;
import com.sora.service.UserService;
import com.sora.utils.excel.ExcelUtils;
import jakarta.annotation.Resource;
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
public class UserController {


    private final UserService userService;

    @Resource(name = "ObjectMapperService")
    private ObjectMapper objectMapperService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * 登陆
     * @return
     */
    @GetMapping("select/{name}/{password}")
    public Result selectUserList(@PathVariable("name") String name,@PathVariable("password") String password) {
        // 从数据库获取所有用户
        return userService.login(name,password);
    }


    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("select")
    public Result selectUserList() {
        // 从数据库获取所有用户
        return userService.select();
    }


    /**
     * 添加用户
     * @return
     */
    @PostMapping("insert")
    public Result selectUserList(@RequestBody User user) {
        // 从数据库获取所有用户
        return userService.insert(user);
    }


    /**
     * 导出所有用户
     */
    @GetMapping("export/excel")
    public void exportUserList() {
        // 从数据库获取所有用户
        List<User> userList = (List<User>)userService.select().getData();
        // 导出
        ExcelUtils.exportToExcel(userList,"员工信息", User.class);
    }
}
