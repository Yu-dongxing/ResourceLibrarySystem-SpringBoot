package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.DTO.RoleCreateDTO;
import top.yuxs.resourcelibrarysystem.DTO.RoleUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.Role;
import top.yuxs.resourcelibrarysystem.pojo.Permission;

import java.util.List;

public interface RoleService {
    // 获取所有角色
    List<Role> getRoleAll();
    
    // 获取单个角色
    Role getRoleById(Integer roleId);
    
    // 创建新角色
    void createRole(RoleCreateDTO roleDTO);
    
    // 更新角色信息
    void updateRole(Integer roleId, RoleUpdateDTO roleDTO);
    
    // 删除角色
    void deleteRole(Integer roleId);
    
    // 获取角色的所有权限
    List<Permission> getRolePermissions(Integer roleId);
    
    // 检查角色是否存在
    boolean exists(Integer roleId);
}
