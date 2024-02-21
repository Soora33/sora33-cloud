package com.sora.service;

import com.sora.domain.permissions.request.RoleMenuVo;
import com.sora.result.Result;

/**
 * @Classname MenuService
 * @Description
 * @Date 2024/02/06 15:43
 * @Author by Sora33
 */
public interface MenuService {

    /**
     * 根据角色id获取对应菜单列表
     * @param id
     * @return
     */
    Result selectMenuById(String id);


    /**
     * 根据所有菜单列表
     * @return
     */
    Result selectMenu();

    /**
     * 根据角色id修改角色所对应菜单权限
     * @param roleMenuVo
     * @return
     */
    Result roleMenuByRoleId(RoleMenuVo roleMenuVo);

}
