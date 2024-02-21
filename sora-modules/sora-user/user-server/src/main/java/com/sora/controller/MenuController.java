package com.sora.controller;

import com.sora.domain.permissions.request.RoleMenuVo;
import com.sora.result.Result;
import com.sora.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname RoleController
 * @Description 菜单控制器
 * @Date 2024/02/06 14:59
 * @Author by Sora33
 */
@RestController
@RequestMapping("/menu")
@Tag(name="menuController", description = "菜单控制器")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "根据所有菜单列表", description = "根据所有菜单列表")
    @GetMapping("select/menu")
    public Result selectMenu() {
        return menuService.selectMenu();
    }


    @Operation(summary = "根据角色id获取对应菜单列表", description = "根据角色获取对应菜单列表")
    @GetMapping("select/menu/{id}")
    public Result selectMenuById(@PathVariable("id") String id) {
        return menuService.selectMenuById(id);
    }


    @Operation(summary = "根据角色id修改角色所对应菜单权限", description = "根据角色id修改角色所对应菜单权限")
    @PutMapping("update/roleMenuByRoleId")
    public Result roleMenuByRoleId(@RequestBody RoleMenuVo roleMenuVo) {
        return menuService.roleMenuByRoleId(roleMenuVo);
    }
}
