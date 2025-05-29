package top.yuxs.resourcelibrarysystem.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.UserLoginLogMapper;
import top.yuxs.resourcelibrarysystem.pojo.UserLoginLog;
import top.yuxs.resourcelibrarysystem.service.UserLoginLogService;
import top.yuxs.resourcelibrarysystem.utils.SysConfigUtil;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;
    @Autowired
    private SysConfigUtil sysConfigUtil;

    @Override
    public void AddUserLoginLog(Long userId, String userName, HttpServletRequest request, String ipAdder) {
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setLoginTime(LocalDateTime.now());
        userLoginLog.setLoginIp(ipAdder);
        userLoginLog.setLoginUserId(userId);
        userLoginLog.setLoginUserAccessLogId(1L);
        String cs = sysConfigUtil.getSysConfigById("isUserLoginLog");
        if (cs.equals("true")){
            userLoginLogMapper.add(userLoginLog);
        }else {
            log.info("已关闭用户登录日志！");
        }
    }

    @Override
    public List<UserLoginLog> findByUser(Long userId) {
        return userLoginLogMapper.findByUserId(userId);
    }

    @Override
    public List<UserLoginLog> findByUserByN(Long userId, Integer n) {

        return userLoginLogMapper.findByUserIdByN(userId,n);
    }
}
