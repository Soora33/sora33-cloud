package com.sora.controller;

import com.sora.domain.permissions.request.UserRoleVo;
import com.sora.result.Result;
import com.sora.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname RoleController
 * @Description 角色控制器
 * @Date 2024/02/06 14:51
 * @Author by Sora33
 */
@RestController
@RequestMapping("/role")
@Tag(name="roleController", description = "角色控制器")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "获取所有角色", description = "获取所有角色")
    @GetMapping("select/role")
    public Result selectRole() {
        return roleService.selectRole();
    }

    @Operation(summary = "根据用户id获取该用户角色", description = "根据用户id获取该用户角色")
    @GetMapping("select/role/{id}")
    public Result selectRoleById(@PathVariable("id") String id) {
        return roleService.selectRoleById(id);
    }

    @Operation(summary = "根据用户id修改用户角色", description = "根据用户id修改用户角色")
    @PutMapping("update/userRoleByUserId")
    public Result updateUserRoleByUserId(@RequestBody UserRoleVo userRoleVo) {
        return roleService.updateUserRoleByUserId(userRoleVo);
    }
}
