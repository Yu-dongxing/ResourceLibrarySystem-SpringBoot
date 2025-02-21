package top.yuxs.resourcelibrarysystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.groups.Default;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class Resource {
    @NotNull(groups = Update.class)
    private Long id;
    private String name;
    private String url;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String author;
    private String tab;
    private String img;
    private String resourceFileId;
    private String desc;
    @JsonIgnore
    private int isDeleted;
    public interface Update extends Default {

    }
} 