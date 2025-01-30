package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.RoleMapper;
import top.yuxs.resourcelibrarysystem.pojo.Permission;
import top.yuxs.resourcelibrarysystem.pojo.Role;
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
    public void createRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.update(role);
    }

    @Override
    public void deleteRole(Integer roleId) {
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
