package top.yuxs.resourcelibrarysystem.service;

import jakarta.servlet.http.HttpServletRequest;
import top.yuxs.resourcelibrarysystem.pojo.UserLoginLog;

import java.util.List;

public interface UserLoginLogService {

    void AddUserLoginLog(Long userId, String userName, HttpServletRequest request, String ipAdder);

    List<UserLoginLog> findByUser(Long userId);
}
