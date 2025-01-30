package top.yuxs.resourcelibrarysystem.service;

import java.util.List;

public interface RolePermissionsService {
    // 为角色分配权限
    void assignPermissions(Integer roleId, List<Integer> permissionIds);
    
    // 移除角色的权限
    void removePermissions(Integer roleId, List<Integer> permissionIds);
    
    // 获取角色的所有权限ID
    List<Integer> getPermissionsByRoleId(Integer roleId);
    
    // 检查角色是否拥有特定权限
    boolean hasPermission(Integer roleId, Integer permissionId);
}
