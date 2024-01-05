package com.sora.service;

import com.sora.result.Result;
import com.sora.user.UserLog;

/**
 * @Classname UserLogService
 * @Description
 * @Date 2023/11/28 13:17
 * @Author by Sora33
 */
public interface UserLogService{

    /**
     * 保存用户操作日志
     * @param userLog
     * @return
     */
    Result insert(UserLog userLog);
}
