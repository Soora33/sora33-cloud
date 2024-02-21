package com.sora.domain.permissions;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Classname SoraMenu
 * @Description 菜单表
 * @Date 2024/02/05 14:54
 * @Author by Sora33
 */
@Data
@Table(value = "sora_menu")
public class SoraMenu {
    @Id(keyType = KeyType.Auto)
    private Integer id;
    private String name;
    private Integer status;
    private String index;
    private String icon;
    private Integer parentId;
    private Date createTime;
    private Integer deleted;

    @Column(ignore = true)
    private String value;
    @Column(ignore = true)
    private List<SoraMenu> children;
    @Column(ignore = true)
    private String menuId;
    @Column(ignore = true)
    private String label;
}
