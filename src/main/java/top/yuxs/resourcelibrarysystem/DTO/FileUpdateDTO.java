package top.yuxs.resourcelibrarysystem.DTO;

import lombok.Data;
import java.util.List;

@Data
public class FileUpdateDTO {
    private Long fileId;  // 如果是更新现有文件则需要
    private String fileName;
    private String fileType;
    private Long fileSize;
} 