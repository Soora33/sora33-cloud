package com.sora.service.impl;

import com.sora.mapper.UserLogMapper;
import com.sora.result.Result;
import com.sora.service.UserLogService;
import com.sora.user.UserLog;
import org.springframework.stereotype.Service;

/**
 * @Classname UserLogServiceImpl
 * @Description
 * @Date 2023/11/28 13:17
 * @Author by Sora33
 */
@Service
public class UserLogServiceImpl implements UserLogService {

    private final UserLogMapper userLogMapper;

    public UserLogServiceImpl(UserLogMapper userLogMapper) {
        this.userLogMapper = userLogMapper;
    }

    /**
     * 保存用户操作日志
     * @param userLog
     * @return
     */
    @Override
    public Result insert(UserLog userLog) {
        return userLogMapper.insertSelective(userLog) > 0 ? Result.success() : Result.error();
    }
}
