package top.yuxs.resourcelibrarysystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysInfo {
    private Long infoId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime infoCreateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime infoUpdateTime;

    private String infoTitle;
    private String infoP;
    private String infoView;
    private String infoDesc;
    private String infoType;
}
