package com.sora.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sora.user.UserLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname UserMapper
 * @Description 用户Mapper
 * @Date 2023/11/27 14:45
 * @Author by Sora33
 */
@Mapper
public interface UserLogMapper extends BaseMapper<UserLog> {
}
