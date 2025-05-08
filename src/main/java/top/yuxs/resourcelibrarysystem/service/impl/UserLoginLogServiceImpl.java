package top.yuxs.resourcelibrarysystem.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.UserLoginLogMapper;
import top.yuxs.resourcelibrarysystem.pojo.UserLoginLog;
import top.yuxs.resourcelibrarysystem.service.UserLoginLogService;
import top.yuxs.resourcelibrarysystem.utils.IPUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;


    @Override
    public void AddUserLoginLog(Long userId, String userName, HttpServletRequest request, String ipAdder) {
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setLoginTime(LocalDateTime.now());
        userLoginLog.setLoginIp(ipAdder);
        userLoginLog.setLoginUserId(userId);
        userLoginLog.setLoginUserAccessLogId(1L);
        userLoginLogMapper.add(userLoginLog);
    }

    @Override
    public List<UserLoginLog> findByUser(Long userId) {
        return userLoginLogMapper.findByUserId(userId);
    }
}
