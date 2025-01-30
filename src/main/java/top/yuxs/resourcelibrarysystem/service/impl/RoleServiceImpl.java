package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.RoleMapper;
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
}
