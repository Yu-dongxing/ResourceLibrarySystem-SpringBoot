package top.yuxs.resourcelibrarysystem.DTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ResourceFileDTO {
    private String name;
    private String author;
    private String tab;
    private String img;
    private String url;
    private String desc;
    private String resourceFileId;
}
