package com.sora.service;

import com.sora.domain.permissions.request.UserRoleVo;
import com.sora.result.Result;

/**
 * @Classname RoleService
 * @Description
 * @Date 2024/02/06 14:52
 * @Author by Sora33
 */
public interface RoleService {

    /**
     * 获取所有角色
     * @return
     */
    Result selectRole();

    /**
     * 根据用户id获取该用户角色
     * @param id
     * @return
     */
    Result selectRoleById(String id);

    /**
     * 根据用户id修改用户角色
     * @param userRoleVo
     * @return
     */
    Result updateUserRoleByUserId(UserRoleVo userRoleVo);
}
