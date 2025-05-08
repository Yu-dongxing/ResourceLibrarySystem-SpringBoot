package top.yuxs.resourcelibrarysystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysOperLog {
    private Long operId;  //
    private String title;
    private Integer businessType; //业务类型（0其它 1新增 2修改 3删除）
    private String method;//方法名称
    private String requestMethod;//请求方式
    private Integer operatorType;//操作类别（0其它 1后台用户 2手机端用户）
    private String operName;//操作人员
    private Long   operUserId;//操作用户id
    private String deptName;//部门名称
    private String operUrl;//请求URL
    private String operIp;//主机地址
    private String operLocation;//操作地点
    private String operParam;//请求参数
    private String jsonResult;//返回参数
    private Integer status;//操作状态（0正常 1异常）
    private String errorMsg;//错误消息
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operTime;//操作时间
}
