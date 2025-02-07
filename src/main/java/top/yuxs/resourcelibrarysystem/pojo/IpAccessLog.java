package top.yuxs.resourcelibrarysystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class IpAccessLog {
    private Long id;
    private String ipAddress;
    private String ipCity;
    private String ipProvince;
    private Long ipRepeat;
    private String ipUserDevice;
    private String ipUserAgent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ipAccessTime;
}
