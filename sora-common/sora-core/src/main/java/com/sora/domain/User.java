package com.sora.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Classname User
 * @Description 用户表
 * @Date 2023/11/22 10:16
 * @Author by Sora33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sora_user")
public class User {
    /**
     * 主键
     */
    private String id;
    /**
     * 用户名
     */
    @Excel(name = "用户名")
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phone;
    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;
    /**
     * 权限id
     */
    private Integer roleLevel;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date createTime;
    /**
     * 是否被封禁
     */
    @Excel(name = "是否被封禁", replace = {"是_1","否_0"})
    private String banned;
    /**
     * 是否被禁用
     */
    @Excel(name = "是否被禁用", replace = {"是_1","否_0"})
    private String disabled;
}
