package com.sora.domain.permissions.request;

import lombok.Data;

/**
 * @Classname UserRoleVo
 * @Description 用户角色接收对象
 * @Date 2024/02/07 09:56
 * @Author by Sora33
 */
@Data
public class RoleMenuVo {
    private String roleId;
    private String[] menuIds;
}
