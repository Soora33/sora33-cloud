package com.sora.domain.permissions;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * @Classname SoraMenuRole
 * @Description 菜单角色关联表
 * @Date 2024/02/05 15:03
 * @Author by Sora33
 */
@Data
@Table(value = "sora_menu_role")
public class SoraMenuRole {
    @Id(keyType = KeyType.Auto)
    private Integer id;
    private Integer roleId;
    private Integer menuId;
}
