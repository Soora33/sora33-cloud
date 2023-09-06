package com.sora.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sora.domain.user.Employee;
import com.sora.mapper.UserMapper;
import com.sora.utils.excel.ExcelUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @Resource
    private UserMapper userMapper;


    @GetMapping("export/excel")
    public void exportUserList() {
        // 从数据库获取所有用户
        List<Employee> employeeList = userMapper.selectList(Wrappers.lambdaQuery(Employee.class));

        // 导出
        ExcelUtils.exportToExcel(employeeList,"员工信息", Employee.class);

    }

    @GetMapping("import/excel")
    public void importUserList(@RequestParam("file")MultipartFile multipartFile) {
        // 获取导入的excel对应集合
        List<Employee> employeeList = ExcelUtils.importExcelToList(multipartFile, Employee.class);

        employeeList.forEach(System.out::println);

    }

    @GetMapping("download")
    public void downFile() {
        // 下载
        ExcelUtils.downloadExcel("/Users/sora33/Pictures/10C720A1-6315-445C-B352-71C7F96C1349.jpeg");
    }
}
