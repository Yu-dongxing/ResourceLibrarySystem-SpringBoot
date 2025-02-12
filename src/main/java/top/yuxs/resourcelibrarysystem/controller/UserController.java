package top.yuxs.resourcelibrarysystem.controller;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.pojo.Users;
import top.yuxs.resourcelibrarysystem.pojo.Role;
import top.yuxs.resourcelibrarysystem.pojo.Permission;
import top.yuxs.resourcelibrarysystem.DTO.UserDTO;
import top.yuxs.resourcelibrarysystem.service.UserService;
import top.yuxs.resourcelibrarysystem.DTO.UserRegisterDTO;
import top.yuxs.resourcelibrarysystem.DTO.UserUpdateDTO;
import top.yuxs.resourcelibrarysystem.DTO.PasswordUpdateDTO;
import top.yuxs.resourcelibrarysystem.annotation.RequiresPermission;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/resources")
public class UserController {
    @Autowired
    private UserService userService;
    
    //注册接口（待修改）
    @PostMapping("/sign")
    public Result<String> register(@RequestBody @Valid UserRegisterDTO registerDTO) {
        if(registerDTO.getRoleId()==1){
            return Result.error("角色id错误");
        }else {
            try {
                userService.register(registerDTO);
                return Result.success("注册成功");
            } catch (RuntimeException e) {
                return Result.error(e.getMessage());
            }
        }
    }
    //登录接口
    @PostMapping("/login")
    public Result<String> login(String phoneNumber, String password) {
        Users loginUser = userService.findPhoneNumber(phoneNumber);
        //判断用户是否存在
        if(loginUser == null) {
            return Result.error("用户手机号错误" + phoneNumber + password);
        }
        if(password.equals(loginUser.getPassword())) {
            // 获取用户角色
            List<Role> roles = userService.getUserRoles(loginUser.getId());
            String roleName = roles.isEmpty() ? "user" : roles.get(0).getName();
            
            StpUtil.login(loginUser.getId(), SaLoginConfig
                    .setExtra("userId", loginUser.getId())
                    .setExtra("username", loginUser.getUsername())
                    .setExtra("phoneNumber", loginUser.getPhoneNumber())
                    .setExtra("role", roleName));  // 添加角色信息
                    
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return Result.success(tokenInfo.tokenValue);
        } else {
            return Result.error("密码错误，请重新输入");
        }
    }
    //获取用户信息
    @GetMapping("/userInfo")
    public Result<Users> selectById(){
        long userId = StpUtil.getLoginIdAsLong();
        Users userinfo  = userService.selectById(userId);
        return Result.success(userinfo);
    }

    // 获取当前用户的详细信息（包含角色和权限）
    @CrossOrigin
    @GetMapping("/user/details")
    public Result<UserDTO> getCurrentUserDetails() {
        long userId = StpUtil.getLoginIdAsLong();
        UserDTO userDTO = userService.getUserDetails(userId);
        return Result.success(userDTO);
    }

    // 获取当前用户的角色列表
    @GetMapping("/user/roles")
    public Result<List<Role>> getCurrentUserRoles() {
        long userId = StpUtil.getLoginIdAsLong();
        List<Role> roles = userService.getUserRoles(userId);
        return Result.success(roles);
    }

    // 获取当前用户的权限列表
    @GetMapping("/user/permissions")
    public Result<List<Permission>> getCurrentUserPermissions() {
        long userId = StpUtil.getLoginIdAsLong();
        List<Permission> permissions = userService.getUserPermissions(userId);
        return Result.success(permissions);
    }

    // 更新用户信息
//    @PutMapping("/user/update")
//    public Result<String> updateUserInfo(@RequestBody @Valid UserUpdateDTO updateDTO) {
//        try {
//            long userId = StpUtil.getLoginIdAsLong();
//            userService.updateUserInfo(userId, updateDTO);
//            return Result.success("更新成功");
//        } catch (RuntimeException e) {
//            return Result.error(e.getMessage());
//        }
//    }

    // 修改密码
    @PutMapping("/user/password")
    public Result<String> updatePassword(@RequestBody @Valid PasswordUpdateDTO passwordDTO) {
        try {
            long userId = StpUtil.getLoginIdAsLong();
            userService.updatePassword(userId, passwordDTO);
            return Result.success("密码修改成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    // 获取所有用户详细信息
    @GetMapping("/admin/users")
//    @RequiresPermission("user:view")
    public Result<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsersDetails();
        return Result.success(users);
    }
    
    // 删除用户
    @DeleteMapping("/admin/users/{userId}")
//    @RequiresPermission("user:delete")
    public Result<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return Result.success("用户删除成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    // 更新用户完整信息
    @PutMapping("/admin/users/{userId}")
//    @RequiresPermission("user:update")
    public Result<String> updateUserComplete(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateDTO updateDTO) {
        String userRole = (String) StpUtil.getExtra("role");
        try {
            if (updateDTO.getRoleId() == 1 && !"admin".equals(userRole)){
                return Result.error("角色错误");
            }else {
                userService.updateUserComplete(userId, updateDTO);
                return Result.success("用户信息更新成功");
            }
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
