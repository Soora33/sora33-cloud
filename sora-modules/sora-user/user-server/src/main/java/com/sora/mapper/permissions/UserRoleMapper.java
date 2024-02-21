package com.sora.mapper.permissions;

import com.mybatisflex.core.BaseMapper;
import com.sora.domain.permissions.SoraUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname UserMapper
 * @Description
 * @Date 2023/07/01 14:38
 * @Author by Sora33
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<SoraUserRole> {
}
