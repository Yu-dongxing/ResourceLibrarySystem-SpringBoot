package top.yuxs.resourcelibrarysystem.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UpdateLogDTO {
    private String logTitle;
    private String desc;
    private String type;
    private String hollow;
}
