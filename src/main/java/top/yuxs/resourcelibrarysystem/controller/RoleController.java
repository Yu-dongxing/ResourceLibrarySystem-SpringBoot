package top.yuxs.resourcelibrarysystem.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.DTO.RoleCreateDTO;
import top.yuxs.resourcelibrarysystem.DTO.RoleUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.pojo.Role;
import top.yuxs.resourcelibrarysystem.pojo.Permission;
import top.yuxs.resourcelibrarysystem.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin // 允许跨域请求
public class RoleController {
    @Autowired
    private RoleService roleService;

    // 获取所有角色
    @GetMapping("/public/getRoles")
    public Result<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getRoleAll();
        return Result.success(roles);
    }

    // 获取角色详情
    @GetMapping("/roles/{roleId}")
    public Result<Role> getRoleById(@PathVariable Integer roleId) {
        Role role = roleService.getRoleById(roleId);
        return Result.success(role);
    }

    // 创建角色
    @PostMapping("/roles")
    public Result<String> createRole(@RequestBody @Valid RoleCreateDTO roleDTO) {
        try {
            roleService.createRole(roleDTO);
            return Result.success("角色创建成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    // 更新角色
    @PutMapping("/roles/{roleId}")
    public Result<String> updateRole(@PathVariable Integer roleId, 
                                   @RequestBody @Valid RoleUpdateDTO roleDTO) {
        try {
            roleService.updateRole(roleId, roleDTO);
            return Result.success("角色更新成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    // 删除角色
    @DeleteMapping("/roles/{roleId}")
    public Result<String> deleteRole(@PathVariable Integer roleId) {
        try {
            roleService.deleteRole(roleId);
            return Result.success("角色删除成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    // 获取角色的权限列表
    @GetMapping("/roles/{roleId}/permissions")
    public Result<List<Permission>> getRolePermissions(@PathVariable Integer roleId) {
        List<Permission> permissions = roleService.getRolePermissions(roleId);
        return Result.success(permissions);
    }
}
