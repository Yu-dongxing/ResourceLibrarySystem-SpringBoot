package top.yuxs.resourcelibrarysystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FileData {
    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;

    private String filePath;
    private String fileMd5;
    private Integer isDeleted;
    private Long tenantId;
}
