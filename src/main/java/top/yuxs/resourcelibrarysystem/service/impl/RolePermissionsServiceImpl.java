package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yuxs.resourcelibrarysystem.mapper.RolePermissionsMapper;
import top.yuxs.resourcelibrarysystem.service.RolePermissionsService;

import java.util.List;

@Service
public class RolePermissionsServiceImpl implements RolePermissionsService {
    @Autowired
    private RolePermissionsMapper rolePermissionsMapper;

    @Override
    @Transactional
    public void assignPermissions(Integer roleId, List<Integer> permissionIds) {
        for (Integer permissionId : permissionIds) {
            rolePermissionsMapper.insert(roleId, permissionId);
        }
    }

    @Override
    @Transactional
    public void removePermissions(Integer roleId, List<Integer> permissionIds) {
        for (Integer permissionId : permissionIds) {
            rolePermissionsMapper.delete(roleId, permissionId);
        }
    }

    @Override
    public List<Integer> getPermissionsByRoleId(Integer roleId) {
        return rolePermissionsMapper.selectPermissionIdsByRoleId(roleId);
    }

    @Override
    public boolean hasPermission(Integer roleId, Integer permissionId) {
        List<Integer> permissions = getPermissionsByRoleId(roleId);
        return permissions.contains(permissionId);
    }
} 