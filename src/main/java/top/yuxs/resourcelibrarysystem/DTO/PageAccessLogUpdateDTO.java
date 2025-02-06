package top.yuxs.resourcelibrarysystem.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PageAccessLogUpdateDTO {
    private Integer userId; // 访问用户的ID
    private String sessionId; // 用户会话ID
    private String pageUrl; // 访问的页面URL，必填
    private String refererUrl; // 来源页面URL
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessTime; // 访问时间，默认为当前时间
    private String ipAddress; // 用户IP地址
    private String userAgent; // 浏览器用户代理信息
    private Integer responseTime; // 页面响应时间（毫秒）
    private Integer statusCode; // HTTP状态码
    private String deviceType; // 访问设备类型（如PC、移动端）
    private String logDetails; // 日志详细信息
}
