package top.yuxs.resourcelibrarysystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginLog {
//    用户登录日志实体类
    private Long id;
    private Long loginUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;
    private String loginIp;
    private Long loginUserAccessLogId;
}
