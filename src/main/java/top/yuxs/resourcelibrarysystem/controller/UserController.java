package top.yuxs.resourcelibrarysystem.controller;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.pojo.Users;
import top.yuxs.resourcelibrarysystem.service.UserRolesService;
import top.yuxs.resourcelibrarysystem.service.UserService;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin // 允许跨域请求
public class UserController {
    @Autowired
    private UserService userservice;
    @Autowired
    private UserRolesService userRolesService;
    //注册接口（待修改）
    @PostMapping("/sign")
    public Result<String> sign(@RequestBody Users users ){
        userservice.add(users);
        return Result.success("注册成功");
    }
    //登录接口
    @PostMapping("/login")
    public Result<String> login(String phoneNumber,String password){
        Users loginUser = userservice.findPhoneNumber(phoneNumber);
        //判断用户是否存在
        if(loginUser==null){
            return Result.error("用户名错误");
        }
        if(password.equals(loginUser.getPassword())){
            StpUtil.login(loginUser.getId(), SaLoginConfig
                    .setExtra("userId", loginUser.getId())
                    .setExtra("username", loginUser.getUsername())
                    .setExtra("phoneNumber", loginUser.getPhoneNumber()));
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return Result.success(tokenInfo.tokenValue);
        }else {
            return Result.error("密码错误，请重新输入");
        }
    }
    //获取用户信息
    @GetMapping("/userInfo")
    public Result<Users> selectById(){
        long userId = StpUtil.getLoginIdAsLong();
        Users userinfo  = userservice.selectById(userId);
        return Result.success(userinfo);
    }
}
