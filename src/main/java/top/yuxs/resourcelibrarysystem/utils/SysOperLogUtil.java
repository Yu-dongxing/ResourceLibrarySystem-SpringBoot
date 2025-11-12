package top.yuxs.resourcelibrarysystem.utils;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yuxs.resourcelibrarysystem.pojo.SysOperLog;
import top.yuxs.resourcelibrarysystem.service.SysOperLogService;

import java.time.LocalDateTime;

@Log4j2
@Component
public class SysOperLogUtil {
    @Autowired
    private SysOperLogService sysOperLogService;
    @Autowired
    private IPUtils ipUtils;

    @Autowired
    private SysConfigUtil sysConfigUtil;

    /**
     * @param title 日志主题
     * @param businessType  业务类型（0其它 1新增 2修改 3删除）
     * @param method   方法名称
     * @param requestMethod  请求方式
     * @param operatorType  操作类别（0：其它 1：后台用户 2：手机端用户）
     * @param deptName  部门名称
     * @param operUrl  请求URL
     * @param operLocation 主机地址
     * @param operParam 操作地点
     * @param jsonResult 请求参数
     * @param status 操作状态（0正常 1异常）
     * @param errorMsg 错误消息
     * @param request 返回参数
     */
    public void add(String title,
             Integer businessType,
             String method,
             String requestMethod,
             Integer operatorType,
             String deptName,
             String operUrl,
             String operLocation,
             String operParam,
             String jsonResult,
             Integer status,
             String errorMsg,
             HttpServletRequest request
    ){

        SysOperLog syslog = new SysOperLog();
        syslog.setOperTime(LocalDateTime.now());
        syslog.setOperIp(ipUtils.getIpAddr(request));
        if(StpUtil.isLogin()){
            syslog.setOperUserId(StpUtil.getLoginIdAsLong());
            syslog.setOperName((String) StpUtil.getExtra("username"));
        }else {
            syslog.setOperUserId(0L);
            syslog.setOperName("访客用户");
        }
        syslog.setTitle(title);
        syslog.setBusinessType(businessType);
        syslog.setMethod(method);
        syslog.setOperatorType(operatorType);
        syslog.setErrorMsg(errorMsg);
        syslog.setDeptName(deptName);
        syslog.setOperParam(operParam);
        syslog.setStatus(status);
        syslog.setRequestMethod(requestMethod);
        syslog.setOperUrl(operUrl);
        syslog.setOperLocation(operLocation);
        syslog.setJsonResult(jsonResult);
        String cs = sysConfigUtil.getSysConfigById("isSysLog");
        if(cs.equals("true")){
            sysOperLogService.add(syslog);
        }else {
            log.info("已关闭系统日志！");
        }

    }
}
