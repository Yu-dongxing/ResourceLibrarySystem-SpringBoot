package top.yuxs.resourcelibrarysystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskUserFinish {
    private Long id;
    private Long taskId;
    private Long userId;
    private String postUserName;
    private String userFinishDesc;
    private String userFinishDescResoursefileid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creatTime;
}
