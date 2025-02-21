package top.yuxs.resourcelibrarysystem.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysInfoUpdateDTO {
    private String infoTitle;
    private String infoP;
    private String infoView;
    private String infoDesc;
    private String infoType;
}
