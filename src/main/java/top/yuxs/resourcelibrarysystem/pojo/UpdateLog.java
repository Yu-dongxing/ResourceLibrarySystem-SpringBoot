package top.yuxs.resourcelibrarysystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateLog {
    private Integer logId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logCreatTime;
    private String logTitle;
    private String desc;
    private String type;
    private String hollow;
}
