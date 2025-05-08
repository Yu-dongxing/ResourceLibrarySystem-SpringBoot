package top.yuxs.resourcelibrarysystem.controller;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.pojo.UserLoginLog;
import top.yuxs.resourcelibrarysystem.service.UserLoginLogService;
import top.yuxs.resourcelibrarysystem.utils.IPUtils;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("${Api-Web-Name.url}")
public class UserLoginLogController {
    @Autowired
    private UserLoginLogService userLoginLogService;
    @Autowired
    private IPUtils ipUtils;
//    添加用户登录日志@RequestBody UserLoginLog userLoginLog,
    @PostMapping("/userloginlog/add")
    Result<String> addUserLoginLog(HttpServletRequest request){
        String userName = (String) StpUtil.getExtra("username");
        String ipAdder = ipUtils.getIpAddr(request);
        Long userId = StpUtil.getLoginIdAsLong();

        userLoginLogService.AddUserLoginLog(userId,userName,request,ipAdder);
        return Result.success("添加用户登录日志成功！");
    }
//    查询用户登录日志
    @GetMapping("/userloginlog/find/user")
    Result<List<UserLoginLog>> findByUserLoginLog(){
        Long userId = StpUtil.getLoginIdAsLong();
        List<UserLoginLog> log = userLoginLogService.findByUser(userId);
        return Result.success(log);
    }
}
