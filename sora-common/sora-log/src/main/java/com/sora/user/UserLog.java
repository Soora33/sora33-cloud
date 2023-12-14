package com.sora.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @Classname UserLog
 * @Description 用户日志记录对象
 * @Date 2023/11/27 14:43
 * @Author by Sora33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sora_user_log")
public class UserLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 6282701656034116654L;
    /**
     * 主键
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 类路径
     */
    private String classPath;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 方法描述
     */
    private String description;
    /**
     * 方法执行耗时/毫秒
     */
    private Long methodTime;
    /**
     * 创建时间
     */
    private Date createTime;
}
