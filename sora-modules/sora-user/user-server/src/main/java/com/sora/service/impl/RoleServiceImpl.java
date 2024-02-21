package com.sora.service.impl;

import com.google.common.collect.Lists;
import com.mybatisflex.core.query.QueryChain;
import com.sora.domain.permissions.SoraRole;
import com.sora.domain.permissions.SoraUserRole;
import com.sora.domain.permissions.request.UserRoleVo;
import com.sora.domain.permissions.table.SoraRoleTableDef;
import com.sora.domain.permissions.table.SoraUserRoleTableDef;
import com.sora.mapper.permissions.RoleMapper;
import com.sora.mapper.permissions.UserRoleMapper;
import com.sora.result.Result;
import com.sora.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname RoleServiceImpl
 * @Description
 * @Date 2024/02/06 14:52
 * @Author by Sora33
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;

    public RoleServiceImpl(RoleMapper roleMapper, UserRoleMapper userRoleMapper) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
    }


    /**
     * 获取所有角色
     * @return
     */
    @Override
    public Result selectRole() {
        List<SoraRole> list = QueryChain.of(roleMapper)
                .select(SoraRoleTableDef.SORA_ROLE.ROLE_ID.as("value"))
                .select(SoraRoleTableDef.SORA_ROLE.ROLE_NAME.as("label"))
                .list();
        return Result.success(list);
    }


    /**
     * 根据用户id获取该用户角色
     * @param id
     * @return
     */
    @Override
    public Result selectRoleById(String id) {
        List<SoraUserRole> roleList = QueryChain.of(userRoleMapper)
                .where(SoraUserRoleTableDef.SORA_USER_ROLE.USER_ID.eq(id))
                .list();
        return Result.success(roleList);
    }


    /**
     * 根据用户id修改用户角色
     * @param userRoleVo
     * @return
     */
    @Override
    public Result updateUserRoleByUserId(UserRoleVo userRoleVo) {
        String[] roleIds = userRoleVo.getRoleIds();
        ArrayList<SoraUserRole> list = Lists.newArrayList();

        // 首先删除该用户拥有的所有角色
        userRoleMapper.deleteByCondition(SoraUserRoleTableDef.SORA_USER_ROLE.USER_ID.eq(userRoleVo.getUserId()));

        // 汇总新用户对象并添加
        Arrays.stream(roleIds).forEach(data -> {
            SoraUserRole userRole = new SoraUserRole();
            userRole.setUserId(Long.parseLong(userRoleVo.getUserId()));
            userRole.setRoleId(Integer.parseInt(data));
            list.add(userRole);
        });
        userRoleMapper.insertBatch(list);

        return Result.success();
    }
}
