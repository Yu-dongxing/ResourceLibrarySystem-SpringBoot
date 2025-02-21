package top.yuxs.resourcelibrarysystem.DTO;

import lombok.Data;
import java.util.List;

@Data
public class ResourceCompleteUpdateDTO {
    // 基本资源信息
    private String name;
    private String url;
    private String author;
    private String tab;
    private String img;
    
    // 文件相关信息
    private List<FileUpdateDTO> filesToUpdate;
    private List<Long> fileIdsToDelete;
}

