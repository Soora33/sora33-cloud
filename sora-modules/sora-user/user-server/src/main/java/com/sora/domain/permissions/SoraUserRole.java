package com.sora.domain.permissions;

import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * @Classname SoraUserRole
 * @Description 用户角色表
 * @Date 2024/02/05 15:02
 * @Author by Sora33
 */
@Data
@Table(value = "sora_user_role")
public class SoraUserRole {
    private Integer id;
    private Long userId;
    private Integer roleId;
}
