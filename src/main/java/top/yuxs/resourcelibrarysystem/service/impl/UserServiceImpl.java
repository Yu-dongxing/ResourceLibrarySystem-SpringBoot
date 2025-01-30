package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yuxs.resourcelibrarysystem.mapper.UserMapper;
import top.yuxs.resourcelibrarysystem.pojo.Users;
import top.yuxs.resourcelibrarysystem.pojo.Role;
import top.yuxs.resourcelibrarysystem.pojo.Permission;
import top.yuxs.resourcelibrarysystem.DTO.UserDTO;
import top.yuxs.resourcelibrarysystem.DTO.UserRegisterDTO;
import top.yuxs.resourcelibrarysystem.service.UserService;
import top.yuxs.resourcelibrarysystem.service.RoleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RoleService roleService;

    @Override
    @Transactional
    public void register(UserRegisterDTO registerDTO) {
        // 1. 检查手机号是否已存在
        Users existUser = findPhoneNumber(registerDTO.getPhoneNumber());
        if (existUser != null) {
            throw new RuntimeException("该手机号已被注册");
        }

        // 2. 检查角色是否存在
        if (!roleService.exists(registerDTO.getRoleId())) {
            throw new RuntimeException("指定的角色不存在");
        }

        // 3. 创建用户对象
        Users user = new Users();
        user.setUsername(registerDTO.getUsername());
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setPassword(registerDTO.getPassword()); // 实际应用中应该加密存储
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // 4. 保存用户信息
        userMapper.insert(user);

        // 5. 关联用户和角色
        userMapper.insertUserRole(user.getId(), registerDTO.getRoleId());
    }

    @Override
    public void add(Users users) {
        users.setUpdateTime(LocalDateTime.now());
        users.setCreateTime(LocalDateTime.now());
        userMapper.insert(users);
    }

    @Override
    public Users findPhoneNumber(String phoneNumber) {
        return userMapper.selectByPhoneNumber(phoneNumber);
    }

    @Override
    public Users selectById(long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        return userMapper.selectRolesByUserId(userId);
    }

    @Override
    public List<Permission> getUserPermissions(Long userId) {
        return userMapper.selectPermissionsByUserId(userId);
    }

    @Override
    public UserDTO getUserDetails(Long userId) {
        Users user = userMapper.selectById(userId);
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        // 复制基本信息
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setCreateTime(user.getCreateTime());
        userDTO.setUpdateTime(user.getUpdateTime());

        // 获取用户角色
        List<Role> roles = getUserRoles(userId);
        if (!roles.isEmpty()) {
            Role primaryRole = roles.get(0); // 获取第一个角色作为主要角色
            userDTO.setRoleName(primaryRole.getName());
            userDTO.setRoleId(primaryRole.getId());
        }

        // 获取用户权限
        List<Permission> permissions = getUserPermissions(userId);
        List<String> permissionNames = permissions.stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
        userDTO.setPermissions(permissionNames);

        return userDTO;
    }
}
