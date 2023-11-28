package com.sora.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
@Schema(description = "用户对象")
@TableName("sora_user")
public class User {
    /**
     * 主键
     */
    private String id;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @Excel(name = "用户名")
    private String name;
    /**
     * 性别
     */
    @Schema(description = "性别")
    @Excel(name = "性别", replace = {"男_1","女_0"})
    private String sex;
    /**
     * 出生日期
     */
    @Schema(description = "出生日期")
    @Excel(name = "出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @Excel(name = "手机号")
    private String phone;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Excel(name = "邮箱")
    private String email;
    /**
     * 权限id
     */
    @Schema(description = "权限id")
    private Integer roleLevel;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @Excel(name = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 是否被封禁
     */
    @Schema(description = "是否被封禁")
    @Excel(name = "是否被封禁", replace = {"是_1","否_0"})
    private String banned;
    /**
     * 是否被禁用
     */
    @Schema(description = "是否被禁用")
    @Excel(name = "是否被禁用", replace = {"是_1","否_0"})
    private String disabled;
}
