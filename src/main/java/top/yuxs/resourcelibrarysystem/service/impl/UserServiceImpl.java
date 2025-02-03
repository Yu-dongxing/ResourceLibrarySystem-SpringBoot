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
import top.yuxs.resourcelibrarysystem.DTO.UserUpdateDTO;
import top.yuxs.resourcelibrarysystem.DTO.PasswordUpdateDTO;
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

    @Override
    @Transactional
    public void updateUserInfo(Long userId, UserUpdateDTO updateDTO) {
        // 1. 检查用户是否存在
        Users user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 如果修改手机号，检查新手机号是否已被使用
        if (updateDTO.getPhoneNumber() != null && !updateDTO.getPhoneNumber().equals(user.getPhoneNumber())) {
            Users existUser = findPhoneNumber(updateDTO.getPhoneNumber());
            if (existUser != null && !existUser.getId().equals(userId)) {
                throw new RuntimeException("该手机号已被使用");
            }
            user.setPhoneNumber(updateDTO.getPhoneNumber());
        }

        // 3. 更新其他基本信息
        if (updateDTO.getUsername() != null) {
            user.setUsername(updateDTO.getUsername());
        }
        if (updateDTO.getEmail() != null) {
            user.setEmail(updateDTO.getEmail());
        }
        
        user.setUpdateTime(LocalDateTime.now());
        
        // 4. 更新用户角色
        if (updateDTO.getRoleId() != null) {
            // 检查角色是否存在
            if (!roleService.exists(updateDTO.getRoleId())) {
                throw new RuntimeException("指定的角色不存在");
            }
            
            // 删除原有角色关联
            userMapper.deleteUserRoles(userId);
            
            // 添加新的角色关联
            userMapper.insertUserRole(userId, updateDTO.getRoleId());
        }
        
        // 5. 保存用户信息更新
        userMapper.update(user);
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, PasswordUpdateDTO passwordDTO) {
        // 1. 验证用户是否存在
        Users user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 验证原密码是否正确
        if (!user.getPassword().equals(passwordDTO.getOldPassword())) {
            throw new RuntimeException("原密码不正确");
        }

        // 3. 验证两次输入的新密码是否一致
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new RuntimeException("两次输入的新密码不一致");
        }

        // 4. 更新密码
        user.setPassword(passwordDTO.getNewPassword());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public List<UserDTO> getAllUsersDetails() {
        // 获取所有用户基本信息
        List<Users> users = userMapper.selectAllUsers();
        
        // 转换为UserDTO列表
        return users.stream()
            .map(user -> {
                UserDTO userDTO = new UserDTO();
                // 复制基本信息
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                userDTO.setPhoneNumber(user.getPhoneNumber());
                userDTO.setCreateTime(user.getCreateTime());
                userDTO.setUpdateTime(user.getUpdateTime());
                
                // 获取用户角色
                List<Role> roles = getUserRoles(user.getId());
                if (!roles.isEmpty()) {
                    Role primaryRole = roles.get(0);
                    userDTO.setRoleName(primaryRole.getName());
                    userDTO.setRoleId(primaryRole.getId());
                }
                
                // 获取用户权限
                List<Permission> permissions = getUserPermissions(user.getId());
                List<String> permissionNames = permissions.stream()
                        .map(Permission::getName)
                        .collect(Collectors.toList());
                userDTO.setPermissions(permissionNames);
                
                return userDTO;
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        // 检查用户是否存在
        Users user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 删除用户角色关联
        userMapper.deleteUserRoles(userId);
        
        // 删除用户
        userMapper.deleteUser(userId);
    }

    @Override
    @Transactional
    public void updateUserComplete(Long userId, UserUpdateDTO updateDTO) {
        // 首先执行现有的updateUserInfo方法
        updateUserInfo(userId, updateDTO);
        
        // 如果需要更新额外的信息，可以在这里添加
        // 例如更新其他关联表的数据
    }
}
