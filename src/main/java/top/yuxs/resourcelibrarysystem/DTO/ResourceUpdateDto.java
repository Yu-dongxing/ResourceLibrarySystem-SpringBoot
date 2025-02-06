package top.yuxs.resourcelibrarysystem.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import top.yuxs.resourcelibrarysystem.pojo.Resource;

import java.time.LocalDateTime;

@Data
public class ResourceUpdateDto {
    private String name;
    private String url;
    private String author;
    private String tab;
    private String img;
}
