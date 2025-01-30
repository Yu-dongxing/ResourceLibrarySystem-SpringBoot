package top.yuxs.resourcelibrarysystem.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class RoleUpdateDTO {
    @NotBlank(message = "角色名称不能为空")
    private String name;
    
    private String description;
    
    private List<Integer> permissionIds;
} 