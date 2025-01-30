package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.pojo.Users;
import top.yuxs.resourcelibrarysystem.DTO.UserDTO;
import top.yuxs.resourcelibrarysystem.pojo.Role;
import top.yuxs.resourcelibrarysystem.pojo.Permission;
import top.yuxs.resourcelibrarysystem.DTO.UserRegisterDTO;
import top.yuxs.resourcelibrarysystem.DTO.UserUpdateDTO;
import top.yuxs.resourcelibrarysystem.DTO.PasswordUpdateDTO;

import java.util.List;

public interface UserService {
    void add(Users users);

    Users findPhoneNumber(String phoneNumber);

    Users selectById(long id);
    
    // 新增方法
    // 获取用户的所有角色
    List<Role> getUserRoles(Long userId);
    
    // 获取用户的所有权限
    List<Permission> getUserPermissions(Long userId);
    
    // 获取用户详细信息（包含角色和权限）
    UserDTO getUserDetails(Long userId);

    void register(UserRegisterDTO registerDTO);

    // 更新用户信息
    void updateUserInfo(Long userId, UserUpdateDTO updateDTO);
    
    // 修改密码
    void updatePassword(Long userId, PasswordUpdateDTO passwordDTO);
}
