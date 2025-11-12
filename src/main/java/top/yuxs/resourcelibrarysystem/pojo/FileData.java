package top.yuxs.resourcelibrarysystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FileData {
    private Long id;//文件id
    private String fileName;//文件名
    private String fileType;//文件类型
    private Long fileSize;//文件大小

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;//文件上次日期

    private String filePath; //文件路径
    private String fileMd5; //文件md5
    private Integer isDeleted; //文件是否删除
    private String resourceId; //文件资源关联id
    private String userName;//上传用户名
    private String fileUrl;//文件网络地址
    private String uuidFileName;//uuid文件名
}
