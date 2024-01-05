package com.sora.mapper;

import com.mybatisflex.core.BaseMapper;
import com.sora.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname UserMapper
 * @Description
 * @Date 2023/07/01 14:38
 * @Author by Sora33
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
