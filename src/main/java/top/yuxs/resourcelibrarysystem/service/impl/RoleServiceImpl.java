package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yuxs.resourcelibrarysystem.mapper.RoleMapper;
import top.yuxs.resourcelibrarysystem.DTO.RoleCreateDTO;
import top.yuxs.resourcelibrarysystem.DTO.RoleUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.Role;
import top.yuxs.resourcelibrarysystem.pojo.Permission;
import top.yuxs.resourcelibrarysystem.service.RoleService;

import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoleAll() {
        return roleMapper.selectByAll();
    }

    @Override
    public Role getRoleById(Integer roleId) {
        return roleMapper.selectById(roleId);
    }

    @Override
    @Transactional
    public void createRole(RoleCreateDTO roleDTO) {
        // 1. 创建角色
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        roleMapper.insert(role);

        // 2. 关联权限
        for (Integer permissionId : roleDTO.getPermissionIds()) {
            roleMapper.insertRolePermission(role.getId(), permissionId);
        }
    }

    @Override
    @Transactional
    public void updateRole(Integer roleId, RoleUpdateDTO roleDTO) {
        // 1. 检查角色是否存在
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new RuntimeException("角色不存在");
        }

        // 2. 更新角色基本信息
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        roleMapper.update(role);

        // 3. 更新角色权限
        if (roleDTO.getPermissionIds() != null) {
            // 删除原有权限关联
            roleMapper.deleteRolePermissions(roleId);
            // 添加新的权限关联
            for (Integer permissionId : roleDTO.getPermissionIds()) {
                roleMapper.insertRolePermission(roleId, permissionId);
            }
        }
    }

    @Override
    @Transactional
    public void deleteRole(Integer roleId) {
        // 1. 检查角色是否存在
        if (!exists(roleId)) {
            throw new RuntimeException("角色不存在");
        }

        // 2. 删除角色权限关联
        roleMapper.deleteRolePermissions(roleId);
        
        // 3. 删除角色
        roleMapper.delete(roleId);
    }

    @Override
    public List<Permission> getRolePermissions(Integer roleId) {
        return roleMapper.selectPermissionsByRoleId(roleId);
    }

    @Override
    public boolean exists(Integer roleId) {
        return roleMapper.selectById(roleId) != null;
    }
}
