package com.sora.service.impl;

import com.google.common.collect.Lists;
import com.mybatisflex.core.query.QueryChain;
import com.sora.domain.permissions.SoraMenu;
import com.sora.domain.permissions.SoraMenuRole;
import com.sora.domain.permissions.request.RoleMenuVo;
import com.sora.domain.permissions.table.SoraMenuRoleTableDef;
import com.sora.domain.permissions.table.SoraMenuTableDef;
import com.sora.mapper.permissions.MenuMapper;
import com.sora.mapper.permissions.MenuRoleMapper;
import com.sora.result.Result;
import com.sora.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Classname MenuServiceImpl
 * @Description
 * @Date 2024/02/06 15:43
 * @Author by Sora33
 */
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;
    private final MenuRoleMapper menuRoleMapper;
    private final List<String> ADMIN_ID = Lists.newArrayList("1");

    public MenuServiceImpl(MenuMapper menuMapper, MenuRoleMapper menuRoleMapper) {
        this.menuMapper = menuMapper;
        this.menuRoleMapper = menuRoleMapper;
    }


    /**
     * 根据角色id获取对应菜单列表
     * @param id
     * @return
     */
    @Override
    public Result selectMenuById(String id) {
        // 判断是否是管理员
        if (ADMIN_ID.contains(id)) {
            List<SoraMenu> list = QueryChain.of(menuMapper)
                    .select(SoraMenuTableDef.SORA_MENU.ID.as("menuId"))
                    .list();
            return Result.success(list);
        }

        List<SoraMenuRole> list = QueryChain.of(menuRoleMapper)
                .where(SoraMenuRoleTableDef.SORA_MENU_ROLE.ROLE_ID.eq(id))
                .list();
        return Result.success(list);
    }


    /**
     * 根据所有菜单列表
     * @return
     */
    @Override
    public Result selectMenu() {
        List<SoraMenu> list = QueryChain.of(menuMapper)
                .select(SoraMenuTableDef.SORA_MENU.ID.as("value"))
                .select(SoraMenuTableDef.SORA_MENU.NAME.as("label"))
                .select(SoraMenuTableDef.SORA_MENU.PARENT_ID.as("parentId"))
                .list();

        List<SoraMenu> soraMenus = this.buildMenuTree(list);
        System.out.println(soraMenus);

        return Result.success(soraMenus);
    }


    /**
     * 根据角色id修改角色所对应菜单权限
     * @param roleMenuVo
     * @return
     */
    @Override
    public Result roleMenuByRoleId(RoleMenuVo roleMenuVo) {
        String[] menuIds = roleMenuVo.getMenuIds();
        ArrayList<SoraMenuRole> list = Lists.newArrayList();

        // 首先删除该角色拥有的所有菜单权限
        menuRoleMapper.deleteByCondition(SoraMenuRoleTableDef.SORA_MENU_ROLE.ROLE_ID.eq(roleMenuVo.getRoleId()));

        // 汇总新用户对象并添加
        Arrays.stream(menuIds).forEach(data -> {
            SoraMenuRole menuRole = new SoraMenuRole();
            menuRole.setRoleId(Integer.parseInt(roleMenuVo.getRoleId()));
            menuRole.setMenuId(Integer.parseInt(data));
            list.add(menuRole);
        });
        menuRoleMapper.insertBatch(list);

        return Result.success();
    }


    /**
     * 根据菜单集合 返回具有父子层级的菜单集合
     * 1. 将集合以id为键，对象为值存入到map，此时获得全量map
     * 2. 获取父节点集合
     * 3. 循环原集合，将所有非父对象存入到父对象下的chidren集合内
     * @param items
     * @return
     */
    public static List<SoraMenu> buildMenuTree(List<SoraMenu> items) {
        Map<String, SoraMenu> itemMap = new HashMap<>();
        List<SoraMenu> rootItems = new ArrayList<>();

        // 首先将所有item按value存入map，方便后续快速查找父节点
        for (SoraMenu item : items) {
            SoraMenu simplifiedItem = new SoraMenu(); // 创建新对象，只包含需要的属性
            simplifiedItem.setValue(item.getValue());
            simplifiedItem.setLabel(item.getLabel());
            itemMap.put(item.getValue(), simplifiedItem);

            if (item.getParentId() == null || item.getParentId() == 0) { // 根节点
                rootItems.add(simplifiedItem);
            }
        }

        // 然后遍历items，根据parentId将节点放到对应父节点的children中
        for (SoraMenu item : items) {
            if (item.getParentId() != null && item.getParentId() != 0) {
                SoraMenu parent = itemMap.get(item.getParentId().toString());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        // 当发现子节点时才初始化children
                        parent.setChildren(new ArrayList<>());
                    }
                    SoraMenu child = itemMap.get(item.getValue());
                    parent.getChildren().add(child);
                }
            }
        }

        // 当发现子节点时才初始化children
        return rootItems;
    }

}
