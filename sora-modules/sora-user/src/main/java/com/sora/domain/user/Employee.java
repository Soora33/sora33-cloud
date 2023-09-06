package com.sora.domain.user;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Classname Employee
 * @Description 企业员工类
 * @Date 2023/07/01 14:30
 * @Author by Sora33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "employees")
public class Employee {
    /**
     * id
     */
    private Integer id;
    /**
     * 员工的名字。
     */
    @Excel(name = "员工名字")
    private String name;
    /**
     * 员工的年龄。
     */
    @Excel(name = "年龄")
    private Integer age;
    /**
     * 员工的薪水，单位为元。
     */
    @Excel(name = "薪水/万", width = 12)
    private String salary;
    /**
     * 员工所在的部门。
     */
    @Excel(name = "所在部门")
    private String department;
    /**
     * 员工的职位。
     */
    @Excel(name = "所属职位")
    private String position;
    /**
     * 员工被雇佣的日期。
     */
    @Excel(name = "入职日期", format = "yyyy-MM-dd")
    private Date dateHired;
    /**
     * 员工的电话号码。
     */
    @Excel(name = "电话")
    private String phone;
    /**
     * 员工的电子邮件地址。
     */
    @Excel(name = "邮箱")
    private String email;
    /**
     * 员工的家庭地址。
     */
    @Excel(name = "住址")
    private String address;
    /**
     * 员工的婚姻状况。
     */
    @Excel(name = "婚姻状况")
    private String maritalStatus;
    /**
     * 员工的出生日期。
     */
    @Excel(name = "出生日期", format = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 员工的性别。'M'表示男性，'F'表示女性。
     */
    @Excel(name = "性别", replace = {"男_M","女_F"})
    private String gender;
    /**
     * 员工的国籍。
     */
    @Excel(name = "国籍")
    private String nationality;
    /**
     * 员工的在职状态。例如，'在职'表示该员工当前仍在公司工作。
     */
    @Excel(name = "在职状态")
    private String employeeStatus;
}
