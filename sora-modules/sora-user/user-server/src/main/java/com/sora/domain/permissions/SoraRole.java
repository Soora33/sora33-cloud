package com.sora.domain.permissions;

import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * @Classname SoraRole
 * @Description 角色表
 * @Date 2024/02/05 15:01
 * @Author by Sora33
 */
@Data
@Table(value = "sora_role")
public class SoraRole {
    private Integer roleId;
    private String roleName;
    private String description;

    private String value;
    private String label;
}
